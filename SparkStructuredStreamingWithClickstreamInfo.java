package org.example;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.from_json;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.streaming.OutputMode;
import org.apache.spark.sql.streaming.StreamingQuery;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class SparkStructuredStreamingWithClickstreamInfo {
    public static void main(String[] args) throws TimeoutException, StreamingQueryException {

        // Set up Spark configuration and session
        SparkConf conf = new SparkConf().setMaster("local[*]").setAppName("SparkStructuredStreamingWithClickstreamInfo");
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
        spark.sparkContext().setLogLevel("ERROR");

        // Define the schema for ClickstreamInfo
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
        Dataset<Row> res = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
                .withColumn("value", from_json(col("value"), structType))
                .select(col("value.*"));

        // Create a temporary view for SQL operations
        res.createOrReplaceTempView("clickstream_data");

        // Define and start streaming queries

        StreamingQuery query2 = res.sqlContext().sql("SELECT deviceType, COUNT(*) AS purchase_intent_count FROM clickstream_data WHERE purchaseIntent = true GROUP BY deviceType ORDER BY purchase_intent_count DESC")
                .writeStream()
                .format("console")
                .outputMode(OutputMode.Complete())
                .start();

        StreamingQuery query3 = res.sqlContext().sql("SELECT referrerURL, COUNT(*) AS referral_count FROM clickstream_data GROUP BY referrerURL ORDER BY referral_count DESC LIMIT 4")
                .writeStream()
                .format("console")
                .outputMode(OutputMode.Complete())
                .start();

        StreamingQuery query4 = res.sqlContext().sql("SELECT discountAvailability, stockAvailability, COUNT(*) AS purchase_intent_count FROM clickstream_data WHERE purchaseIntent = true GROUP BY discountAvailability, stockAvailability")
                .writeStream()
                .format("console")
                .outputMode(OutputMode.Complete())
                .start();

        // Await termination of all queries

        query2.awaitTermination();
        query3.awaitTermination();
        query4.awaitTermination();
    }
}
