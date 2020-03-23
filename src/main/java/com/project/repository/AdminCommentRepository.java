package com.project.repository;

import com.project.model.AdminComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminCommentRepository extends JpaRepository<AdminComment, Long> {
}
