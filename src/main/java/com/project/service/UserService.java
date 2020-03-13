package com.project.service;

import com.project.exception.UserNotFoundException;
import com.project.model.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(final long id) throws UserNotFoundException;

}
