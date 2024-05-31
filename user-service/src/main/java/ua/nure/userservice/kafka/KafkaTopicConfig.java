package ua.nure.userservice.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

public class KafkaTopicConfig {

    @Bean
    public KafkaAdmin.NewTopics topics(){
        return new KafkaAdmin.NewTopics(
                TopicBuilder.name("image.upload")
                        .replicas(2)
                        .build(),
                TopicBuilder.name("image.upload.response")
                        .partitions(2)
                        .build());

    }
}
