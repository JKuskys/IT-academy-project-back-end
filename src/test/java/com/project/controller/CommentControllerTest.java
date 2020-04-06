package com.project.controller;

import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;
import com.project.service.CommentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.mockito.Mockito.when;

public class CommentControllerTest {

    @InjectMocks
    private CommentController commentController;

    @Mock
    private CommentService commentService;

    @Mock
    private CommentResponse comment;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(commentService.getById(1L)).thenReturn(comment);
        when(commentService.addAdminComment(new CommentRequest(), 1L)).thenReturn(comment);
        when(commentService.updateAdminComment(new CommentRequest(), 1L, 1L)).thenReturn(comment);
    }

    @Test
    public void shouldSucceedInFetchingComment() throws Exception {
        ResponseEntity<CommentResponse> response = commentController.fetchComment(1L);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldSucceedInFetchingVisibleComments() {
        ResponseEntity<List<CommentResponse>> response = commentController.fetchApplicantVisibleComments(1L);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldSucceedInCreatingComment() throws Exception {
        ResponseEntity<CommentResponse> response = commentController.createComment(new CommentRequest(), 1L);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldSucceedInUpdatingComment() throws Exception {
        ResponseEntity<CommentResponse> response = commentController.updateComment(new CommentRequest(), 1L, 1L);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldSucceedInDeletingComment() throws Exception {
        ResponseEntity<HttpStatus> response = commentController.deleteComment(1L);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
