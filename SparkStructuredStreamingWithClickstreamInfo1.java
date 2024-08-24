package org.example;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.from_json;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.spark.SparkConf;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.ml.feature.StringIndexer;
import org.apache.spark.ml.feature.VectorAssembler;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;

public class SparkStructuredStreamingWithClickstreamInfo1 {
    public static void main(String[] args) throws TimeoutException, StreamingQueryException {

        SparkConf conf = new SparkConf().setMaster("local").setAppName("SparkStructuredStreamingWithKafka");
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
        spark.sparkContext().setLogLevel("ERROR");

        // Define schema (same as before)
        List<StructField> geoLocationFields = new ArrayList<>();
        geoLocationFields.add(DataTypes.createStructField("Lon", DataTypes.StringType, true));
        geoLocationFields.add(DataTypes.createStructField("Lat", DataTypes.StringType, true));

        List<StructField> fields = new ArrayList<>();
        fields.add(DataTypes.createStructField("userID", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("sessionID", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("timeOfDay", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("referrerURL", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("deviceType", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("browserType", DataTypes.StringType, true));
        fields.add(DataTypes.createStructField("purchaseIntent", DataTypes.BooleanType, true));
        fields.add(DataTypes.createStructField("discountAvailability", DataTypes.BooleanType, true));
        fields.add(DataTypes.createStructField("stockAvailability", DataTypes.BooleanType, true));
        fields.add(DataTypes.createStructField("purchaseAmount", DataTypes.DoubleType, true));
        fields.add(DataTypes.createStructField("pageDuration", DataTypes.DoubleType, true));
        fields.add(DataTypes.createStructField("userDemographics", DataTypes.createArrayType(DataTypes.StringType), true));
        fields.add(DataTypes.createStructField("geoLocation", DataTypes.createStructType(geoLocationFields), true));

        StructType structType = DataTypes.createStructType(fields);

        // Read streaming data from Kafka
        Dataset<Row> df = spark
                .readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "bigstream") // Kafka topic name
                .load();

        // Transform the data using the defined schema
        Dataset<Row> clickstreamDF = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
                .withColumn("value", from_json(col("value"), structType))
                .select(col("value.*"));

        // Feature engineering: Encode categorical features
        StringIndexer deviceIndexer = new StringIndexer()
                .setInputCol("deviceType")
                .setOutputCol("deviceTypeIndex");

        StringIndexer browserIndexer = new StringIndexer()
                .setInputCol("browserType")
                .setOutputCol("browserTypeIndex");

        // Assemble features into a single vector
        VectorAssembler assembler = new VectorAssembler()
                .setInputCols(new String[]{"deviceTypeIndex", "browserTypeIndex", "purchaseAmount", "pageDuration"})
                .setOutputCol("features");

        // Define the model
        LogisticRegression lr = new LogisticRegression()
                .setLabelCol("purchaseIntent")
                .setFeaturesCol("features");

        // Create a pipeline
        Pipeline pipeline = new Pipeline().setStages(new PipelineStage[]{deviceIndexer, browserIndexer, assembler, lr});

        // Train the model on a historical dataset (you should have a separate dataset for training)
        Dataset<Row> historicalData = loadHistoricalData(spark);
        PipelineModel model = pipeline.fit(historicalData);

        // Apply the model to the streaming data
        Dataset<Row> predictions = model.transform(clickstreamDF);

        // Output the predictions to the console
        StreamingQuery query = predictions.select("userID", "sessionID", "prediction")
                .writeStream()
                .format("console")
                .outputMode("update")
                .start();

        query.awaitTermination();
    }
    private static Dataset<Row> loadHistoricalData(SparkSession spark) {
        // Load your historical data from a JSON file for training the model
        return spark.read().option("inferSchema", "true").json("clickstream-info1.json");
    }

}
