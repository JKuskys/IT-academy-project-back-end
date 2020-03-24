package com.project.model.request;

import com.project.model.AdminComment;
import com.project.model.response.CommentResponse;
import com.project.service.AdminCommentService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestComment {

    private String comment;

    private String date;

    private long applicationId;

    private long authorId;

}
