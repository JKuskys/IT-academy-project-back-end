package com.project.service;

import com.project.model.User;

public interface EmailService {
    void sendEmail(String recipient, String subject, String message);

    void constructResetTokenEmail(String contextPath, String token, User user);
}
