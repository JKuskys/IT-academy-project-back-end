package com.project.service;

import com.project.exception.UserEmailExistsException;
import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.repository.UserRepository;
import com.project.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public List<User> getAll() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public User getById(final long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User addUser(User user) throws UserException {
        userValidator.validate(user);

        user.setId(null);//do not allow choosing id

        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        if (userRepository.findByEmail(user.getEmail()).isPresent())
            throw new UserEmailExistsException(user.getEmail());

        List<String> roles = new ArrayList<>();
        roles.add("User");
        user.setRoles(roles); //probably more logical as we don't have admin registration

        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user, long id) throws UserException {
        if (!userRepository.findById(id).isPresent())
            throw new UserNotFoundException(id);

        userValidator.validate(user);

        if (userRepository.findByEmail(user.getEmail()).isPresent()
                && userRepository.findByEmail(user.getEmail()).get().getId() != id)
            throw new UserEmailExistsException(user.getEmail());

        return userRepository.findById(id)
                .map(existingUser -> {
                    //updating is_admin field is not allowed
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    return userRepository.save(existingUser);
                })
                .orElse(null);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        if (!userRepository.findById(id).isPresent())
            throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }
}
