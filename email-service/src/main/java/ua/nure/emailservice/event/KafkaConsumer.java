package ua.nure.emailservice.event;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ua.nure.emailservice.model.EmailDetails;
import ua.nure.emailservice.service.IEmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final String KAFKA_TOPIC = "email-service-send-email";
    private final String KAFKA_GROUP_ID = "email_service_group";
    private final IEmailService emailService;

    @KafkaListener(topics = KAFKA_TOPIC, groupId = KAFKA_GROUP_ID)
    public void consumer(EmailDetails emailDetails) {
        log.info("Consuming information from Kafka: {}", emailDetails);
        emailService.sendEmail(emailDetails);
    }
}