package ua.nure.securityservice.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ua.nure.securityservice.model.EmailDetails;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Bean
    public DefaultKafkaProducerFactory<String,EmailDetails> kafkaProducerFactory( @Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {

        Map<String, Object> props = new HashMap<>();
        props.put("bootstrap.servers", bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, EmailDetails> kafkaTemplate(DefaultKafkaProducerFactory<String,EmailDetails> kafkaProducerFactory) {
        return new KafkaTemplate<>(kafkaProducerFactory);
    }

}
