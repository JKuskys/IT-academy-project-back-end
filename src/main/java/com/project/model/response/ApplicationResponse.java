package com.project.model.response;

import com.project.model.Application;
import com.project.model.ApplicationStatus;
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

    private ApplicationStatus status;

    private boolean isNewInternalComment;

    private String lastInternalCommentAuthor;

    private boolean isNewExternalComment;

    private String lastExternalCommentAuthor;

    public ApplicationResponse(Application app) {
        this.id = app.getId();
        this.fullName = app.getApplicant().getFullName();
        this.phoneNumber = app.getPhoneNumber();
        this.education = app.getEducation();
        this.hobbies = app.getHobbies();
        this.isAgreementNeeded = app.isAgreementNeeded();
        this.comment = app.getComment();
        this.isAcademyTimeSuitable = app.isAcademyTimeSuitable();
        this.reason = app.getReason();
        this.technologies = app.getTechnologies();
        this.source = app.getSource();
        this.applicationDate = app.getApplicationDate();
        this.email = app.getApplicant().getEmail();
        this.commentCount = app.getComments().size();
        this.status = app.getStatus();
        this.isNewExternalComment = app.isNewExternalComment();
        this.isNewInternalComment = app.isNewInternalComment();
        this.lastExternalCommentAuthor = app.getLastExternalCommentAuthor();
        this.lastInternalCommentAuthor = app.getLastInternalCommentAuthor();
    }
}
