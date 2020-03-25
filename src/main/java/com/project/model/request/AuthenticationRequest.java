package com.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -6986746375915710855L;

    @NotBlank(message = "Elektroninis paštas negali būti tuščias")
    private String email;

    @NotBlank(message = "Slaptažodis negali būti tuščias")
    private String password;
}
