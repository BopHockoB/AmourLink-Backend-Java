package ua.nure.securityservice.model;

public record EmailDetails (String from, String to, String subject, String text, String pathToAttachment){}