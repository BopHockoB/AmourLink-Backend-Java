package ua.nure.emailservice.service;


import ua.nure.emailservice.model.EmailDetails;

public interface IEmailService {
    void sendEmail(EmailDetails emailDetails);
}
