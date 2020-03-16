package com.project.service;

import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(final long id) throws UserNotFoundException;

    void addUser(User user) throws UserException;

    User updateUser(User user, long id) throws UserException;

    void deleteUser(final long id) throws UserNotFoundException;

}
