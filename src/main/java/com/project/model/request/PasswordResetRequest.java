package com.project.model.request;

import com.project.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetRequest {

    private String email;

    @Password
    private String newPassword;
}
