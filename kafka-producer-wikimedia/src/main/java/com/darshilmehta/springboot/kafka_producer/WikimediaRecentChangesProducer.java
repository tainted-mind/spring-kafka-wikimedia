package com.darshilmehta.springboot.kafka_producer;

import com.darshilmehta.springboot.handler.KafkaMessageHandler;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaRecentChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaRecentChangesProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public WikimediaRecentChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
        EventHandler eventHandler = new KafkaMessageHandler(this.kafkaTemplate, "wikimedia_recent_change");
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}
