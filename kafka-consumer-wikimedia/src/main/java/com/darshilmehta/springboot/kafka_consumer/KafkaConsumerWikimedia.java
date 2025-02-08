package com.darshilmehta.springboot.kafka_consumer;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class KafkaConsumerWikimedia {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerWikimedia.class);

    private final String logFilePath = "C:\\Users\\darshil.mehta\\IdeaProjects\\spring-kafka-event-wikimedia\\kafka-consumer-wikimedia\\src\\main\\resources\\logs.txt";

    @PostConstruct
    public void clearPreviousLogs() {
        try {
            Files.write(Paths.get(logFilePath), "".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(
            topics = "wikimedia_recent_change",
            groupId = "wikimedia-group"
    )
    public void consumeWikimediaEvent(String message) {
        LOGGER.info("Message received at consumer: {}", message);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            writer.newLine();
            writer.write(message);
        }  catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
