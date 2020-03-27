package com.project.model.request;

import com.project.model.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationUpdateRequest {
    private ApplicationStatus status;
}
