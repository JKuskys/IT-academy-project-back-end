package com.project.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "education")
    private String education;

    @Column(name = "free_time")
    private String hobbies;

    @Column(name = "agreement")
    private boolean isAgreementNeeded;

    @Column(name = "comment")
    private String comment;

    @Column(name = "academy_time")
    private boolean isAcademyTimeSuitable;

    @Column(name = "reason")
    private String reason;

    @Column(name = "technologies")
    private String technologies;

    @Column(name = "source")
    private String source;

    @Column(name = "application_date")
    private String applicationDate;

    @Column(name = "status")
    private ApplicationStatus status;

    @OneToMany(mappedBy = "application")
    private List<AdminComment> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Application(String phoneNumber, String education, String hobbies, boolean agreementNeeded, String comment,
                           boolean academyTimeSuitable, String reason, String technologies, String source, String applicationDate,
                           ApplicationStatus status, ArrayList<AdminComment> comments, User user) {
        this.phoneNumber = phoneNumber;
        this.education = education;
        this.hobbies = hobbies;
        this.isAgreementNeeded = agreementNeeded;
        this.comment = comment;
        this.isAcademyTimeSuitable = academyTimeSuitable;
        this.reason = reason;
        this.technologies = technologies;
        this.source = source;
        this.applicationDate = applicationDate;
        this.status = status;
        this.comments = comments;
        this.user = user;
    }
}