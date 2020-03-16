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
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("api/users")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("api/users/{id}")
    ResponseEntity<User> getUser(@PathVariable long id) throws UserNotFoundException {
        return new ResponseEntity<User>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("api/users")
    ResponseEntity<User> postUser(@RequestBody User newUser) throws UserException {
        userService.addUser(newUser);
        return new ResponseEntity<User>(userService.getById(newUser.getId()), HttpStatus.CREATED);
    }

    @PutMapping("api/users/{id}")
    ResponseEntity<User> putUser(@RequestBody User newUser, @PathVariable Long id) throws UserException {
        return new ResponseEntity<User>(userService.updateUser(newUser, id), HttpStatus.OK);
    }

    @DeleteMapping("api/users/{id}")
    ResponseEntity<User> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
