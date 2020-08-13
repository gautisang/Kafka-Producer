package com.kafkasparkstream;

class AppConfigs {
    final static String applicationID = "SparkProducer";
    //final static String bootstrapServers = "localhost:9092,localhost:9093";
    final static String topicName = "spark-producer-topic";
    final static String kafkaConfigFileLocation = "kafka.properties";
    final static int numEvents = 1000000;
    final static String[] eventFiles = {"data/access_log_Aug95","data/access_log_Jul95"};
}
