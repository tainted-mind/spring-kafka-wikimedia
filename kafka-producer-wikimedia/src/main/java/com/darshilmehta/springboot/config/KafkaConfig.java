package com.darshilmehta.springboot.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic wikimediaRecentChangeTopic() {
        return TopicBuilder
                .name("wikimedia_recent_change")
                .build();
    }

}
