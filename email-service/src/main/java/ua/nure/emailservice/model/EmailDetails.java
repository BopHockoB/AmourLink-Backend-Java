package ua.nure.emailservice.model;

public record EmailDetails (String from, String to, String subject, String text, String pathToAttachment){}
