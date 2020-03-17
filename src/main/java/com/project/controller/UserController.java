package com.project.controller;

import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.User;
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

    @GetMapping("")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<User> fetchUser(@PathVariable long id) throws UserNotFoundException {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("")
    ResponseEntity<User> createUser(@RequestBody User newUser) throws UserException {
        User createdUser = userService.addUser(newUser);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<User> updateUser(@RequestBody User newUser, @PathVariable Long id) throws UserException {
        return new ResponseEntity<>(userService.updateUser(newUser, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<User> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
