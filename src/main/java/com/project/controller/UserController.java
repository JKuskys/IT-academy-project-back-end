package com.project.controller;

import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.model.response.UserResponse;
import com.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<User> users = userService.getAll();
        List<UserResponse> response = new ArrayList<>();

        for(User user : users) {
            response.add(new UserResponse(user.getEmail(), user.getFullName()));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponse> fetchUser(@PathVariable long id) throws UserNotFoundException {
        User user = userService.getById(id);
        UserResponse response = new UserResponse(user.getEmail(), user.getFullName());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<HttpStatus> createUser(@RequestBody User user) throws UserException {
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateUser(@RequestBody User user, @PathVariable Long id) throws UserException {
        userService.updateUser(user, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
