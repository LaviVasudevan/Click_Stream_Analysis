# Click Stream Analysis

Real-time clickstream data processing pipeline using Apache Kafka and Apache Spark for analyzing user behavior and generating insights.

## Overview

This project demonstrates a streaming data pipeline that ingests, processes, and analyzes clickstream events in real-time. It simulates user interactions and provides analytical insights into user behavior patterns.

## Architecture

- **Data Generation**: Simulates clickstream events (clicks, page views, user sessions)
- **Message Broker**: Apache Kafka for reliable event streaming
- **Stream Processing**: Apache Spark Structured Streaming for real-time analytics
- **Output**: Processed insights and aggregations

## Features

- Real-time clickstream event generation
- Kafka-based event streaming
- Spark Structured Streaming processing
- Clickstream info extraction and analysis
- Support for multiple Kafka topics
- Scalable distributed processing

## Prerequisites

- Java 8 or higher
- Apache Kafka
- Apache Spark
- Maven (for dependency management)

## Project Structure

```
├── ClickstreamInfo.java                      # Clickstream data model
├── GenerateClickstreamInfo.java              # Data generator
├── GenerateClickstreamInfoStreamToKafka.java # Kafka producer
├── GenerateClickstreamInfoStreamToKafka1.java
├── SparkExample.java                         # Basic Spark examples
├── SparkSqlExample.java                      # Spark SQL examples
├── SparkStreamingExample.java                # Streaming examples
├── SparkStructuredStreamingWithClickstreamInfo...
├── TestKafkaProducer.java                    # Kafka testing
├── RandomDataGenUtility.java                 # Utility for data generation
├── clickstream-info1.json                    # Sample data
└── pom.xml                                   # Maven dependencies
```

## Setup & Installation

1. **Start Kafka**:
```bash
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka
bin/kafka-server-start.sh config/server.properties
```

2. **Create Kafka Topic**:
```bash
bin/kafka-topics.sh --create --topic clickstream-events \
  --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
```

3. **Build Project**:
```bash
mvn clean package
```

4. **Run Data Generator**:
```bash
java -cp target/clickstream-analysis.jar GenerateClickstreamInfoStreamToKafka
```

5. **Run Spark Streaming Job**:
```bash
spark-submit --class SparkStructuredStreamingWithClickstreamInfo \
  --master local[*] target/clickstream-analysis.jar
```

## Use Cases

- Real-time user behavior tracking
- Page view analytics
- Session analysis
- Click pattern detection
- A/B testing analysis
- User journey mapping

## Technologies

- **Apache Kafka**: Distributed streaming platform
- **Apache Spark**: Unified analytics engine
- **Java**: Primary programming language
- **Maven**: Build and dependency management

## Future Enhancements

- Add machine learning models for user behavior prediction
- Implement anomaly detection
- Add visualization dashboard
- Support for multiple data sources
- Enhanced metrics and KPIs
