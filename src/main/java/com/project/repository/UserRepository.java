package com.project.repository;

import com.project.exception.UserNotFoundException;
import com.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email) throws UsernameNotFoundException;
}
