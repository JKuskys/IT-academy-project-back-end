package com.project.controller;

import com.project.exception.AdminCommentNotFoundException;
import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserNotFoundException;
import com.project.model.AdminComment;
import com.project.model.request.RequestComment;
import com.project.model.response.CommentResponse;
import com.project.repository.ApplicationRepository;
import com.project.service.AdminCommentService;
import com.project.service.ApplicationService;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/comments")
public class AdminCommentController {
    private final AdminCommentService commentService;
    private final ApplicationService applicationService;
    private final UserService userService;

    @Autowired
    public AdminCommentController(AdminCommentService commentService, ApplicationService applicationService, UserService userService){
        this.applicationService = applicationService;
        this.userService = userService;
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> fetchComments() {
        List<AdminComment> comments = commentService.getAll();
        List<CommentResponse> response = new ArrayList<>();

        for(AdminComment comment: comments) {
            response.add(new CommentResponse(comment.getId(), comment.getComment(), comment.getAuthor().getEmail(),
                    comment.getCommentDate(), comment.getDateModified()));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> fetchComment(@PathVariable Long id) throws AdminCommentNotFoundException {
        AdminComment comment = commentService.getById(id);
        return new ResponseEntity<>(
                new CommentResponse(comment.getId(), comment.getComment(), comment.getAuthor().getEmail(),
                        comment.getCommentDate(), comment.getDateModified()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createComment(@Valid @RequestBody RequestComment comment) throws ApplicationNotFoundException, UserNotFoundException {
        AdminComment adminComment = new AdminComment(comment.getComment(), comment.getDate(),
                applicationService.getById(comment.getApplicationId()), userService.getByEmail(comment.getAuthorEmail()));
        commentService.addAdminComment(adminComment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateComment(@RequestBody RequestComment comment, @PathVariable Long id)
            throws AdminCommentNotFoundException, ApplicationNotFoundException, UserNotFoundException {
        AdminComment adminComment = new AdminComment(comment.getComment(), comment.getDate(),
                applicationService.getById(comment.getApplicationId()), userService.getByEmail(comment.getAuthorEmail()));
        commentService.updateAdminComment(adminComment, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) throws AdminCommentNotFoundException {
        commentService.deleteAdminComment(id);
        return ResponseEntity.noContent().build();
    }
}
