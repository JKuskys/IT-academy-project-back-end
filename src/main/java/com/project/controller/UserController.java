package com.project.controller;

import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.Application;
import com.project.model.User;
import com.project.model.request.UserCommentRequest;
import com.project.model.request.UserRequest;
import com.project.model.response.ApplicationResponse;
import com.project.model.response.UserResponse;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> fetchUsers() {
        List<User> users = userService.getAll();
        List<UserResponse> response = users.stream().map(user -> new UserResponse(user.getEmail(), user.getFullName()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> fetchUser(@PathVariable Long id) throws UserNotFoundException {
        User user = userService.getById(id);
        UserResponse response = new UserResponse(user.getEmail(), user.getFullName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/application")
    public ResponseEntity<ApplicationResponse> fetchUserApplication(@RequestBody UserCommentRequest email) throws UserNotFoundException {
        Application app = userService.getByEmail(email.getEmail()).getApplication();
        ApplicationResponse response = new ApplicationResponse(app);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<HttpStatus> createUser(@RequestBody UserRequest user) throws UserException {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateUser(@RequestBody UserRequest user, @PathVariable Long id) throws UserException {
        userService.updateUser(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
