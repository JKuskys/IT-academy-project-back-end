package com.project.model.response;

import com.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse implements Serializable {

    private String email;

    private String fullName;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.fullName = user.getFullName();
    }
}
