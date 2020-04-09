package com.project.service;

import com.project.exception.CommentNotFoundException;
import com.project.model.Application;
import com.project.model.Comment;
import com.project.model.User;
import com.project.model.UserRole;
import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;
import com.project.repository.CommentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CommentServiceTest {

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private Comment comment;

    @Mock
    private User user;

    @Mock
    private Application application;

    @Mock
    private CommentRequest commentRequest;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ApplicationServiceImpl applicationService;

    @Mock
    private UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));
        when(comment.getApplication()).thenReturn(application);
        when(application.getId()).thenReturn(1L);

        when(comment.getAuthor()).thenReturn(user);
        when(comment.getId()).thenReturn(1L);
        when(comment.getComment()).thenReturn("test comment");
        when(user.getFullName()).thenReturn("test name");
        when(user.getEmail()).thenReturn("test email");
        when(comment.getCommentDate()).thenReturn("2020-04-20");
        when(comment.getDateModified()).thenReturn(null);
        when(user.getRoles()).thenReturn(Collections.singletonList(UserRole.ADMIN.name()));
    }

    @Test
    public void shouldSucceedInGettingComment() throws Exception {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(comment.isVisibleToApplicant()).thenReturn(false);

        CommentResponse response = commentService.getById(1L);

        Assert.assertEquals(Long.valueOf(1), response.getId());
        Assert.assertEquals("test comment", response.getComment());
        Assert.assertEquals("test name", response.getAuthor());
        Assert.assertEquals("test email", response.getAuthorEmail());
        Assert.assertEquals("2020-04-20", response.getCommentDate());
        Assert.assertNull(response.getDateModified());
        Assert.assertFalse(response.isVisibleToApplicant());
        Assert.assertTrue(response.isAuthorAdmin());
    }

    @Test
    public void shouldSucceedInGettingApplicantVisibleComments() {
        when(comment.isVisibleToApplicant()).thenReturn(true);

        List<CommentResponse> response = commentService.getApplicantVisibleComments(1L);

        Assert.assertEquals(1, response.size());
    }

    @Test
    public void shouldSucceedInGettingApplicantVisibleCommentsWhenThereAreNone() {
        when(comment.isVisibleToApplicant()).thenReturn(false);

        List<CommentResponse> response = commentService.getApplicantVisibleComments(1L);

        Assert.assertEquals(0, response.size());
    }

    @Test(expected = CommentNotFoundException.class)
    public void shouldThrowExceptionWhenCommentDoesNotExist() throws Exception {
        when(commentRepository.findById(10L)).thenThrow(CommentNotFoundException.class);

        commentService.getById(10L);
    }

    @Test
    public void shouldSucceedInSavingComment() throws Exception {
        when(commentRequest.getComment()).thenReturn("test comment");
        when(comment.isVisibleToApplicant()).thenReturn(true);
        when(applicationService.getById(1L)).thenReturn(application);
        when(userService.getByEmail(any(String.class))).thenReturn(user);
        when(commentRequest.getAuthorEmail()).thenReturn("test author email");
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        CommentResponse response = commentService.addAdminComment(commentRequest, 1L);

        Assert.assertEquals(Long.valueOf(1), response.getId());
        Assert.assertEquals("test comment", response.getComment());
        Assert.assertEquals("test name", response.getAuthor());
        Assert.assertEquals("test email", response.getAuthorEmail());
        Assert.assertEquals("2020-04-20", response.getCommentDate());
        Assert.assertNull(response.getDateModified());
        Assert.assertTrue(response.isVisibleToApplicant());
        Assert.assertTrue(response.isAuthorAdmin());
    }
}
