package com.project.model.response;

import com.project.model.Comment;
import com.project.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse implements Serializable {
    private Long id;

    private String comment;

    private String author;

    private String authorEmail;

    private String commentDate;

    private String dateModified;

    private boolean isVisibleToApplicant;

    private boolean isAuthorAdmin;

    private String attachmentName;

    public CommentResponse (Comment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.author = comment.getAuthor().getFullName();
        this.authorEmail = comment.getAuthor().getEmail();
        this.commentDate = comment.getCommentDate();
        this.dateModified = comment.getDateModified();
        this.isVisibleToApplicant = comment.isVisibleToApplicant();
        this.isAuthorAdmin = comment.getAuthor().getRoles().contains(UserRole.ADMIN.name());
        this.attachmentName = comment.getAttachmentName();
    }
}
