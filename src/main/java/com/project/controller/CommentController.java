package com.project.controller;

import com.project.exception.CommentAttachmentNotFoundException;
import com.project.exception.CommentNotFoundException;
import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserNotFoundException;
import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;
import com.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/applications/{appId}/comments")
public class CommentController {
    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> fetchComments(@PathVariable("appId") Long appId) {
        return new ResponseEntity<>(commentService.getAll(appId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponse> fetchComment(@PathVariable("id") Long id) throws CommentNotFoundException {
        return new ResponseEntity<>(commentService.getById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/attachment", produces = MediaType.APPLICATION_PDF_VALUE)
    public @ResponseBody byte[] fetchCommentAttachment(@PathVariable("id") Long id) throws CommentNotFoundException, CommentAttachmentNotFoundException {
        try {
            return commentService.getAttachmentById(id);
        } catch (IOException e) {
            throw new CommentAttachmentNotFoundException(id);
        }
    }

    @GetMapping("/applicant/visible")
    public ResponseEntity<List<CommentResponse>> fetchApplicantVisibleComments(@PathVariable("appId") Long appId) {
        return new ResponseEntity<>(commentService.getApplicantVisibleComments(appId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(@Valid @RequestBody CommentRequest comment, @PathVariable("appId") Long appId)
            throws ApplicationNotFoundException, UserNotFoundException {
        return new ResponseEntity<>(commentService.addAdminComment(comment, appId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponse> updateComment(@Valid @RequestBody CommentRequest comment, @PathVariable("id") Long id, @PathVariable("appId") Long appId)
            throws CommentNotFoundException {
        return new ResponseEntity<>(commentService.updateAdminComment(comment, id, appId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable Long id) throws CommentNotFoundException {
        commentService.deleteAdminComment(id);
        return ResponseEntity.noContent().build();
    }
}
