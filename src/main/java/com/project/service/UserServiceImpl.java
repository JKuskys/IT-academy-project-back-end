package com.project.service;

import com.project.exception.UserEmailExistsException;
import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.PasswordResetToken;
import com.project.model.User;
import com.project.model.UserRole;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;
import com.project.repository.PasswordTokenRepository;
import com.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder bcryptEncoder;
    private final PasswordTokenRepository passwordTokenRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bcryptEncoder, PasswordTokenRepository passwordTokenRepository) {
        this.userRepository = userRepository;
        this.bcryptEncoder = bcryptEncoder;
        this.passwordTokenRepository = passwordTokenRepository;
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
        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new UserEmailExistsException(userRequest.getEmail());
        }

        userRequest.setPassword(bcryptEncoder.encode(userRequest.getPassword()));

        User user = new User(userRequest);

        List<String> roles = new ArrayList<>();
        roles.add(UserRole.USER.name());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    @Override
    public UserResponse updateUser(UserRequest user, Long id) throws UserException {
        Optional<User> existingUser = userRepository.findById(id);

        if (!existingUser.isPresent()) {
            throw new UserNotFoundException(id);
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()
                && !userRepository.findByEmail(user.getEmail()).get().getId().equals(id)) {
            throw new UserEmailExistsException(user.getEmail());
        }

        User response = existingUser
                .map(existing -> {
                    existing.setEmail(user.getEmail());
                    existing.setPassword(user.getPassword());
                    existing.setFullName(user.getFullName());
                    return userRepository.save(existing);
                }).get();
        return new UserResponse(response);
    }

    @Override
    public void deleteUser(long id) throws UserNotFoundException {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(resetToken);
    }

    @Override
    public void changeUserPassword(User user, String password) {
        user.setPassword(bcryptEncoder.encode(password));
        userRepository.save(user);
    }
}
