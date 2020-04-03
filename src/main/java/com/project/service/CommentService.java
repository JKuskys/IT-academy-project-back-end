package com.project.service;

import com.project.exception.CommentAttachmentNotFoundException;
import com.project.exception.CommentNotFoundException;
import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserNotFoundException;
import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;

import java.io.IOException;
import java.util.List;

public interface CommentService {
    List<CommentResponse> getAll(Long appId);

    CommentResponse getById(Long id) throws CommentNotFoundException;

    byte[] getAttachmentById(Long id) throws CommentNotFoundException, IOException, CommentAttachmentNotFoundException;

    List<CommentResponse> getApplicantVisibleComments(Long appId);

    CommentResponse addAdminComment(CommentRequest adminComment, Long appId)
            throws ApplicationNotFoundException, UserNotFoundException;

    CommentResponse updateAdminComment(CommentRequest adminComment, Long id, Long appId)
            throws CommentNotFoundException;

    void deleteAdminComment(Long id) throws CommentNotFoundException;
}
