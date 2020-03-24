package com.project.model.request;

import com.project.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRequest {

    private String phoneNumber;

    private String education;

    private String hobbies;

    private boolean isAgreementNeeded;

    private String comment;

    private boolean isAcademyTimeSuitable;

    private String reason;

    private String technologies;

    private String source;

    private String applicationDate;

    private User user;
}
