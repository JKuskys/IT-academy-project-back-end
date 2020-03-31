package com.project.model.request;

import com.project.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUpdateRequest {
    @NotNull(message = "Statusas negali būti tuščias")
    private ApplicationStatus status;
}
