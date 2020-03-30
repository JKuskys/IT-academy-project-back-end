package com.project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {

    @Size(max = 1500, message = "Komentaras negali būti ilgesnis nei 1500 simboliai")
    private String comment;

    @NotBlank(message = "Komentaro autoriaus elektroninis paštas negali būti nenurodytas")
    private String authorEmail;

    private boolean isVisibleToApplicant;
}
