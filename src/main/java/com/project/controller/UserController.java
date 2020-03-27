package com.project.controller;

import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.Application;
import com.project.model.request.UserCommentRequest;
import com.project.model.request.UserRequest;
import com.project.model.response.ApplicationResponse;
import com.project.model.response.UserResponse;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> fetchUser(@PathVariable Long id) throws UserNotFoundException {
        return new ResponseEntity<>(new UserResponse(userService.getById(id)), HttpStatus.OK);
    }

    @PostMapping("/application")
    public ResponseEntity<ApplicationResponse> fetchUserApplication(@RequestBody UserCommentRequest email) throws UserNotFoundException {
        Application app = userService.getByEmail(email.getEmail()).getApplication();
        ApplicationResponse response = new ApplicationResponse(app);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody UserRequest user) throws UserException {
        return new ResponseEntity<>(new UserResponse(userService.addUser(user)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponse> updateUser(@RequestBody UserRequest user, @PathVariable Long id) throws UserException {
        return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
