package com.telerikacademy.car_service.services.contracts;

public interface EmailService {

    void sendMessageWithAttachment(String to,
                                   String subject,
                                   String text);
}
