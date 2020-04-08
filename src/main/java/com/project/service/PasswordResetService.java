package com.project.service;

public interface PasswordResetService {
    String validatePasswordResetToken(Long id, String token);
}
