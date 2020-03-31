package com.project.model.request;

import com.project.validation.Password;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @Email(message = "Elektroninis paštas turi būti validus")
    @NotEmpty(message = "Elektroninis paštas privalo būti nurodytas")
    private String email;

    @Password
    private String password;

    @NotBlank(message = "Vardas negali būti tuščias")
    @Size(max = 255, message = "Vardas negali būti ilgesnis nei 255 simboliai")
    private String fullName;
}
