package com.project.service;

import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;

import java.util.List;

public interface UserService {

    List<UserResponse> getAll();

    User getById(final Long id) throws UserNotFoundException;

    User getByEmail(final String email) throws UserNotFoundException;

    User addUser(UserRequest user) throws UserException;

    UserResponse updateUser(UserRequest user, Long id) throws UserException;

    void deleteUser(final long id) throws UserNotFoundException;

    void createPasswordResetTokenForUser(User user, String token);

    void changeUserPassword(User user, String password);
}
