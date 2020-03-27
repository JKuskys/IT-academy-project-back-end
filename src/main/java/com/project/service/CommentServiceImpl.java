package com.project.service;

import com.project.exception.AdminCommentNotFoundException;
import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserNotFoundException;
import com.project.model.Comment;
import com.project.model.request.CommentRequest;
import com.project.model.response.CommentResponse;
import com.project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CommentResponse getById(Long id) throws AdminCommentNotFoundException {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new AdminCommentNotFoundException(id));
        return new CommentResponse(comment);
    }

    @Override
    public CommentResponse addAdminComment(CommentRequest comment, Long appId)
            throws ApplicationNotFoundException, UserNotFoundException {

        Comment adminComment = new Comment(comment.getComment(), comment.getCommentDate(),
                applicationService.getById(appId), userService.getByEmail(comment.getAuthorEmail()));

        return new CommentResponse(commentRepository.save(adminComment));
    }

    @Override
    public CommentResponse updateAdminComment(CommentRequest commentRequest, Long id, Long appId) throws AdminCommentNotFoundException {

        return new CommentResponse(commentRepository.findById(id)
                .map(existingComment -> {
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    existingComment.setComment(commentRequest.getComment());
                    existingComment.setDateModified(dateFormat.format(new Date()));
                    return commentRepository.save(existingComment);
                }).orElseThrow(() -> new AdminCommentNotFoundException(id)));
    }

    @Override
    public void deleteAdminComment(Long id) throws AdminCommentNotFoundException {
        if(!commentRepository.findById(id).isPresent()){
            throw new AdminCommentNotFoundException(id);
        }
        commentRepository.deleteById(id);
    }
}
