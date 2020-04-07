package com.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendEmail(String recipient, String subject, String message) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(recipient);
        msg.setSubject(subject);
        msg.setText(message);

        javaMailSender.send(msg);
    }
}
