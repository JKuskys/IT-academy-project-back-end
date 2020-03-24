package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin_comments")
public class AdminComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 1500, message = "Komentaras negali būti ilgesnis nei 1500 simboliai")
    @Column(name = "comment")
    private String comment;

    @NotBlank(message = "Komentaro data negali būti tuščia")
    @Column(name = "comment_date")
    private String commentDate;

    @Column(name = "date_modified")
    private String dateModified;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    public AdminComment(String comment, String date, Application application, User user) {
        this.comment = comment;
        this.commentDate = date;
        this.application = application;
        this.author = user;
    }
}