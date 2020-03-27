package com.project.model.response;

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
}
