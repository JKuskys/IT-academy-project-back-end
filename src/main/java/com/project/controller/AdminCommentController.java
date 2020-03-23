package com.project.controller;

import com.project.exception.AdminCommentNotFoundException;
import com.project.model.AdminComment;
import com.project.model.CommentResponse;
import com.project.service.AdminCommentService;
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

    @Autowired
    public AdminCommentController(AdminCommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> fetchComments() {
        List<AdminComment> comments = commentService.getAll();
        List<CommentResponse> response = new ArrayList<>();

        for(AdminComment comment: comments) {
            response.add(new CommentResponse(comment.getComment(), comment.getAuthor().getEmail(), comment.getCommentDate()));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> fetchComment(@PathVariable Long id) throws AdminCommentNotFoundException {
        AdminComment comment = commentService.getById(id);
        return new ResponseEntity<>(
                new CommentResponse(comment.getComment(), comment.getAuthor().getEmail(), comment.getCommentDate()),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createComment(@Valid @RequestBody AdminComment comment) {
        commentService.addAdminComment(comment);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HttpStatus> updateComment(@RequestBody AdminComment comment, @PathVariable Long id) throws AdminCommentNotFoundException {
        commentService.updateAdminComment(comment, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) throws AdminCommentNotFoundException {
        commentService.deleteAdminComment(id);
        return ResponseEntity.noContent().build();
    }
}
