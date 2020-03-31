package com.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserApplicationRequest {
    @NotBlank(message = "Elektroninis paštas yra privalomas")
    private String email;
}
