package com.project.service;

import com.project.model.User;
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

    @Override
    public void constructResetTokenEmail(String contextPath, String token, User user) {
        String url = String.format("%s/change-password;id=%d;token=%s", contextPath, user.getId(), token);
        String message = "Norėdami pasikeisti slaptažodį, paspauskite žemiau esančią nuorodą";
        sendEmail(user.getEmail(), "Pasikeisti slaptažodį", message + "\r\n" + url);
    }
}
