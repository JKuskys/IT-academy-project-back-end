package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "application_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "comment_date")
    private String commentDate;

    @Column(name = "date_modified")
    private String dateModified;

    @Column(name = "is_visible_to_student")
    private boolean isVisibleToApplicant;

    @Column(name = "attachment_name")
    private String attachmentName;

    @Column(name = "attachment")
    private byte[] attachment;

    @ManyToOne
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    public Comment(String comment, String date, boolean isVisible, Application application, User user, String attachmentName) {
        this.comment = comment;
        this.commentDate = date;
        this.application = application;
        this.author = user;
        this.isVisibleToApplicant = isVisible;
        this.attachmentName = attachmentName;
        this.attachment = null;
    }
}
