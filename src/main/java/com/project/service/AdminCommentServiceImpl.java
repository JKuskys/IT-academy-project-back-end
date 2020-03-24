package com.project.service;

import com.project.exception.AdminCommentNotFoundException;
import com.project.model.AdminComment;
import com.project.repository.AdminCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminCommentServiceImpl implements AdminCommentService {

    private final AdminCommentRepository commentRepository;

    @Autowired
    public AdminCommentServiceImpl(AdminCommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<AdminComment> getAll() {
        return new ArrayList<>(commentRepository.findAll());
    }

    @Override
    public AdminComment getById(Long id) throws AdminCommentNotFoundException {
        return commentRepository.findById(id).orElseThrow(() -> new AdminCommentNotFoundException(id));
    }

    @Override
    public AdminComment addAdminComment(AdminComment adminComment) {
        adminComment.setId(null);
        return commentRepository.save(adminComment);
    }

    @Override
    public AdminComment updateAdminComment(AdminComment adminComment, Long id) throws AdminCommentNotFoundException {
        return commentRepository.findById(id)
                .map(existingComment -> {
                    existingComment.setApplication(adminComment.getApplication());
                    existingComment.setAuthor(adminComment.getAuthor());
                    existingComment.setComment(adminComment.getComment());
                    existingComment.setCommentDate(adminComment.getCommentDate());
                    return commentRepository.save(existingComment);
                }).orElseThrow(() -> new AdminCommentNotFoundException(id)) ;
    }

    @Override
    public void deleteAdminComment(Long id) throws AdminCommentNotFoundException {
        if(!commentRepository.findById(id).isPresent()){
            throw new AdminCommentNotFoundException(id);
        }
        commentRepository.deleteById(id);
    }
}
