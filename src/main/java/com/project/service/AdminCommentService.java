package com.project.service;

import com.project.exception.AdminCommentNotFoundException;
import com.project.model.AdminComment;

import java.util.List;

public interface AdminCommentService {
    List<AdminComment> getAll();

    AdminComment getById(Long id) throws AdminCommentNotFoundException;

    AdminComment addAdminComment(AdminComment adminComment);

    AdminComment updateAdminComment(AdminComment adminComment, Long id) throws AdminCommentNotFoundException;

    void deleteAdminComment(Long id) throws AdminCommentNotFoundException;
}
