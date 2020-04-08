package com.project.service;

public interface PasswordResetService {
    String validatePasswordResetToken(long id, String token);
}
