package com.project.service;

import com.project.exception.CommentAttachmentNotFoundException;
import com.project.exception.CommentNotFoundException;
import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserNotFoundException;
import com.project.model.Application;
import com.project.model.Comment;
import com.project.model.User;
import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;
import com.project.repository.CommentRepository;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ApplicationService applicationService;
    private final UserService userService;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ApplicationService applicationService, UserService userService) {
        this.commentRepository = commentRepository;
        this.applicationService = applicationService;
        this.userService = userService;
    }

    @Override
    public List<CommentResponse> getAll(Long appId) {
        List<Comment> comments = new ArrayList<>(commentRepository.findAll());
        return comments.stream().filter(comment -> comment.getApplication().getId().equals(appId)).map(CommentResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse getById(Long id) throws CommentNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        return new CommentResponse(comment);
    }

    @Override
    public byte[] getAttachment(Long id, String filename) throws CommentNotFoundException,
            CommentAttachmentNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));

        if (!comment.getAttachmentName().equals(filename))
            throw new CommentAttachmentNotFoundException(id);

        return comment.getAttachment();
    }

    @Override
    public void addAttachment(Long id, MultipartFile file) throws CommentNotFoundException, IOException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        byte[] bytes = file.getBytes();
        String extention = FilenameUtils.getExtension(file.getOriginalFilename());
        //TODO some validation for the file goes in here

        comment.setAttachment(bytes);
        comment.setAttachmentName(file.getOriginalFilename());
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getApplicantVisibleComments(Long appId) {
        List<CommentResponse> comments = getAll(appId);
        return comments.stream().filter(CommentResponse::isVisibleToApplicant).collect(Collectors.toList());
    }

    @Override
    public CommentResponse addAdminComment(CommentRequest comment, Long appId)
            throws ApplicationNotFoundException, UserNotFoundException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean isExternal = comment.isVisibleToApplicant();
        Application app = applicationService.getById(appId);

        if (isExternal) {
            app.setNewExternalComment(true);
            app.setLastExternalCommentAuthor(comment.getAuthorEmail());
        } else {
            app.setNewInternalComment(true);
            app.setLastInternalCommentAuthor(comment.getAuthorEmail());
        }

        Comment adminComment = new Comment(comment.getComment(), dateFormat.format(new Date()), isExternal,
                app, userService.getByEmail(comment.getAuthorEmail()), null);

        return new CommentResponse(commentRepository.save(adminComment));
    }

    @Override
    public CommentResponse updateAdminComment(CommentRequest commentRequest, Long id, Long appId) throws CommentNotFoundException {
        return new CommentResponse(commentRepository.findById(id)
                .map(existingComment -> {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    existingComment.setComment(commentRequest.getComment());
                    existingComment.setDateModified(dateFormat.format(new Date()));
                    return commentRepository.save(existingComment);
                }).orElseThrow(() -> new CommentNotFoundException(id)));
    }

    @Override
    public void deleteAdminComment(Long id) throws CommentNotFoundException {
        if (!commentRepository.existsById(id)) {
            throw new CommentNotFoundException(id);
        }
        commentRepository.deleteById(id);
    }
}
