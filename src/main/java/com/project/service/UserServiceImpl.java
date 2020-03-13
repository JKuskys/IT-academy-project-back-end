package com.project.service;

import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public User getById(final long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User updateUser(User user, long id) throws UserNotFoundException {
        if(!userRepository.findById(id).isPresent())
            throw new UserNotFoundException(id);
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setAdmin(user.isAdmin());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    return userRepository.save(existingUser);
                })
                .orElse(null);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        if(!userRepository.findById(id).isPresent())
            throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }
}
