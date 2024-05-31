package ua.nure.emailservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import ua.nure.emailservice.model.EmailDetails;

@SpringBootTest
@RequiredArgsConstructor
class EmailServiceTest {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private EmailService emailService;


    @Test
    public void whenSendEmail_thenSendMethodOfJavaMailSenderIsInvokedWithCorrectAttributes() {
        // Instance Creation or Mock of EmailDetails
        EmailDetails emailDetails = new EmailDetails("noreply@amourlink.com",
                "danylo.zemskyi@nure.ua",
                "Test Subject",
                "Test Mail Content",
                null );


        emailService.sendEmail(emailDetails);
    }
}