package com.project.service;

import com.project.exception.*;
import com.project.model.Application;
import com.project.model.Comment;
import com.project.model.User;
import com.project.model.UserRole;
import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;
import com.project.repository.CommentRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ApplicationService applicationService;
    private final UserService userService;
    private final EmailService emailService;
    private final MessageSource messageSource;

    private final List<String> allowedFileExtensions = Arrays.asList("pdf", "doc", "docx");
    private final List<String> allowedContentTypes = Arrays.asList("application/msword", "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/octet-stream");

    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository, ApplicationService applicationService, UserService userService,
            EmailService emailService, MessageSource messageSource) {
        this.commentRepository = commentRepository;
        this.applicationService = applicationService;
        this.userService = userService;
        this.emailService = emailService;
        this.messageSource = messageSource;
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

        if (!comment.getAttachmentName().equals(filename)) {
            throw new CommentAttachmentNotFoundException(id);
        }

        return comment.getAttachment();
    }

    @Override
    public void addAttachment(Long id, MultipartFile file) throws CommentNotFoundException, IOException, InvalidFileException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));
        byte[] bytes = file.getBytes();
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (file.getContentType() != null && !file.getContentType().trim().equals("")) {
            if (!allowedContentTypes.contains(file.getContentType()))
                throw new InvalidFileException(file.getContentType());
        }

        if (!allowedFileExtensions.contains(extension))
            throw new InvalidFileException(extension);

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
        DateFormat dateFormat = new SimpleDateFormat(messageSource.getMessage("dateFormat", null, null));
        boolean isExternal = comment.isVisibleToApplicant();
        Application app = applicationService.getById(appId);
        User author = userService.getByEmail(comment.getAuthorEmail());

        if (isExternal) {
            app.setNewExternalComment(true);
            app.setLastExternalCommentAuthor(comment.getAuthorEmail());
            if (author.getRoles().contains(UserRole.ADMIN.name())) {
                emailService.sendEmail(
                        app.getApplicant().getEmail(), messageSource.getMessage("comService.newCommentHeader", null, null),
                        messageSource.getMessage("comService.newCommentMessage", null, null));
            }
        } else {
            app.setNewInternalComment(true);
            app.setLastInternalCommentAuthor(comment.getAuthorEmail());
        }

        Comment adminComment = new Comment(comment.getComment(), dateFormat.format(new Date()), isExternal,
                app, author, null);

        return new CommentResponse(commentRepository.save(adminComment));
    }

    @Override
    public CommentResponse updateAdminComment(CommentRequest commentRequest, Long id, Long appId) throws CommentNotFoundException {
        return new CommentResponse(commentRepository.findById(id)
                .map(existingComment -> {
                    DateFormat dateFormat = new SimpleDateFormat(messageSource.getMessage("dateFormat", null, null));
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
