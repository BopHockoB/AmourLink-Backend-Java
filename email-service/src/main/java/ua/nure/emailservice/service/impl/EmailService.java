package ua.nure.emailservice.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
import ua.nure.emailservice.model.EmailDetails;
import ua.nure.emailservice.service.IEmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements IEmailService {
    private final JavaMailSender emailSender;

    @Override
    public void sendEmail(EmailDetails emailDetails) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setFrom("Sender Name <noreply@amourlink.com>");
            helper.setTo(emailDetails.to());
            helper.setSubject(emailDetails.subject());
            helper.setText(emailDetails.text());
        } catch (MessagingException e) {
            log.warn(e.getMessage());
        }

        emailSender.send(message);
    }

    //TODO Implement emails with attachment
}
