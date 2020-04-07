package com.project.service;

import com.project.exception.*;
import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommentService {
    List<CommentResponse> getAll(Long appId);

    CommentResponse getById(Long id) throws CommentNotFoundException;

    byte[] getAttachment(Long id, String filename) throws CommentNotFoundException, CommentAttachmentNotFoundException;

    void addAttachment(Long id, MultipartFile file) throws CommentNotFoundException, IOException, InvalidFileException;

    List<CommentResponse> getApplicantVisibleComments(Long appId);

    CommentResponse addAdminComment(CommentRequest adminComment, Long appId)
            throws ApplicationNotFoundException, UserNotFoundException;

    CommentResponse updateAdminComment(CommentRequest adminComment, Long id, Long appId)
            throws CommentNotFoundException;

    void deleteAdminComment(Long id) throws CommentNotFoundException;
}
