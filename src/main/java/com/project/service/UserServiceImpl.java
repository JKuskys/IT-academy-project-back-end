package com.project.service;

import com.project.exception.UserEmailExistsException;
import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.model.UserRoles;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;
import com.project.repository.UserRepository;
import com.project.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private BCryptPasswordEncoder bcryptEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.userValidator = new UserValidator();
        this.bcryptEncoder = bcryptEncoder;
    }

    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @Override
    public User getById(final Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User getByEmail(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    public User addUser(UserRequest userRequest) throws UserException {
        userValidator.validate(userRequest);

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserEmailExistsException(userRequest.getEmail());
        }

        userRequest.setPassword(bcryptEncoder.encode(userRequest.getPassword()));

        User user = new User(userRequest);

        List<String> roles = new ArrayList<>();
        roles.add(UserRoles.USER.toString());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(UserRequest user, Long id) throws UserException {
        if (!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException(id);
        }

        userValidator.validate(user);

        if (userRepository.findByEmail(user.getEmail()).isPresent()
                && !userRepository.findByEmail(user.getEmail()).get().getId().equals(id)) {
            throw new UserEmailExistsException(user.getEmail());
        }

        User response = userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setFullName(user.getFullName());
                    return userRepository.save(existingUser);
                }).get();
        return new UserResponse(response);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        if (!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }
}
