package com.project.controller;

import com.project.exception.AdminCommentNotFoundException;
import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserNotFoundException;
import com.project.model.AdminComment;
import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;
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
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/applications/{appId}/comments")
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
    public ResponseEntity<List<CommentResponse>> fetchComments(@PathVariable("appId") long appId) {
        List<AdminComment> comments = commentService.getAll();

        List<CommentResponse> response = comments.stream().filter(comment -> comment.getApplication().getId() == appId).map(comment -> new CommentResponse(
                comment.getId(), comment.getComment(), comment.getAuthor().getFullName(),
                comment.getAuthor().getEmail(), comment.getCommentDate(), comment.getDateModified()
        )).collect(Collectors.toList());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> fetchComment(@PathVariable("id") Long id) throws AdminCommentNotFoundException {
        AdminComment comment = commentService.getById(id);
        return new ResponseEntity<>(new CommentResponse(
                    comment.getId(), comment.getComment(), comment.getAuthor().getFullName(),
                    comment.getAuthor().getEmail(), comment.getCommentDate(), comment.getDateModified()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentRequest comment, @PathVariable("appId") long appId)
            throws ApplicationNotFoundException, UserNotFoundException {

        AdminComment adminComment = new AdminComment(comment.getComment(), comment.getCommentDate(),
                applicationService.getById(appId), userService.getByEmail(comment.getAuthorEmail()));
        commentService.addAdminComment(adminComment);

        CommentResponse response = new CommentResponse(adminComment.getId(), adminComment.getComment(), adminComment.getAuthor().getFullName(),
                adminComment.getAuthor().getEmail(), adminComment.getCommentDate(), adminComment.getDateModified());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@RequestBody CommentRequest comment, @PathVariable("id") Long id, @PathVariable("appId") long appId)
            throws AdminCommentNotFoundException, ApplicationNotFoundException, UserNotFoundException {

        AdminComment adminComment = new AdminComment(comment.getComment(), comment.getCommentDate(),
                applicationService.getById(appId), userService.getByEmail(comment.getAuthorEmail()));
        commentService.updateAdminComment(adminComment, id);

        CommentResponse response = new CommentResponse(adminComment.getId(), adminComment.getComment(), adminComment.getAuthor().getFullName(),
                adminComment.getAuthor().getEmail(), adminComment.getCommentDate(), adminComment.getDateModified());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) throws AdminCommentNotFoundException {
        commentService.deleteAdminComment(id);
        return ResponseEntity.noContent().build();
    }
}
