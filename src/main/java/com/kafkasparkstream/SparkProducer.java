package com.kafkasparkstream;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class SparkProducer {

    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) throws IOException {
        logger.info("Creating Kafka Producer...");
        Properties props = new Properties();

        //Create Kafka Producer
        props.put(ProducerConfig.CLIENT_ID_CONFIG, AppConfigs.applicationID);
        InputStream inputStream = new FileInputStream(AppConfigs.kafkaConfigFileLocation);
        props.load(inputStream);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        KafkaProducer<Integer, String> producer = new KafkaProducer<>(props);
        int counter = 0;

        //Read the files one by one and send the record to kafka topic
        logger.info("Starting Producer threads...");
        for(int i=0;i<AppConfigs.eventFiles.length;i++) {
            File file = new File(AppConfigs.eventFiles[i]);
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    producer.send(new ProducerRecord<>(AppConfigs.topicName, null, line));
                    counter++;
                }
            }
        }
        producer.close();
        logger.info("Finished Dispatcher Demo");


    }
}
