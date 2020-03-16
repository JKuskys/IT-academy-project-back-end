package com.project.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(name="application")
public class Application {

    @Id
    @NotNull
    private long id;

    @NotBlank
    @NotNull
    @Column(name="full_name")
    private String name;

    @NotBlank
    @NotNull
    @Column(name="phone_number")
    private String phone_number;

    @NotBlank
    @NotNull
    @Column(name="education")
    private String education;

    @NotBlank
    @NotNull
    @Column(name="free_time")
    private String free_time;

    @NotNull
    @Column(name="agreement")
    private boolean agreement;

    @NotBlank
    @NotNull
    @Column(name="comment")
    private String comment;

    @NotNull
    @Column(name="academy_time")
    private boolean academy_time;

    @NotBlank
    @NotNull
    @Column(name="reason")
    private String reason;

    @NotBlank
    @NotNull
    @Column(name="technologies")
    private String technologies;

    @NotBlank
    @NotNull
    @Column(name="source")
    private String source;

    @NotBlank
    @NotNull
    @Column(name="application_date")
    private String application_date;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}