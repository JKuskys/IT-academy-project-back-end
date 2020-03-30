package com.project.model;

import com.project.model.request.ApplicationRequest;
import lombok.*;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private List<Comment> comments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User applicant;

    public Application(ApplicationRequest app, ApplicationStatus status, List<Comment> comments, User applicant) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        this.phoneNumber = app.getPhoneNumber();
        this.education = app.getEducation();
        this.hobbies = app.getHobbies();
        this.isAgreementNeeded = app.isAgreementNeeded();
        this.comment = app.getComment();
        this.isAcademyTimeSuitable = app.isAcademyTimeSuitable();
        this.reason = app.getReason();
        this.technologies = app.getTechnologies();
        this.source = app.getSource();
        this.applicationDate = dateFormat.format(new Date());
        this.status = status;
        this.comments = comments;
        this.applicant = applicant;
    }
}