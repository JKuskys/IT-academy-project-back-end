package com.project.service;

import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.model.request.UserRequest;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(final long id) throws UserNotFoundException;

    User getByEmail(final String email) throws UserNotFoundException;

    User addUser(UserRequest user) throws UserException;

    User updateUser(UserRequest user, long id) throws UserException;

    void deleteUser(final long id) throws UserNotFoundException;


}
