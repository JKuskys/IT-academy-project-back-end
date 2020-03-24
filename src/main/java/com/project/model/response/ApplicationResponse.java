package com.project.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse implements Serializable {

    private Long id;

    private String fullName;

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

    private String email;

    private int commentCount;

    private String status;
}
