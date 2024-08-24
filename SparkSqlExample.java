package org.example;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkSqlExample {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("Spark-Sql-Example");
        SparkSession spark = SparkSession.builder().config(conf).getOrCreate();
        Dataset<Row> df = spark.read().json("clickstream-info1.json");
        df.printSchema();
        df.createOrReplaceTempView("temp");
        Dataset<Row> res = spark.sql("select * from temp where deviceType='tablet'");
        res.write().json("result1.json");
    }
}