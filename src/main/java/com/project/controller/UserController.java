package com.project.controller;

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
    ResponseEntity<User> getUser(@PathVariable long id){
        try {
            return new ResponseEntity<User>(userService.getById(id), HttpStatus.OK);
        } catch (UserNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("api/users")
    ResponseEntity<User> postUser(@RequestBody User newUser) {
        userService.addUser(newUser);
        try {
            return new ResponseEntity<User>(userService.getById(newUser.getId()), HttpStatus.CREATED);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("api/users/{id}")
    ResponseEntity<User> putUser(@RequestBody User newUser, @PathVariable Long id) {
        try {
            return new ResponseEntity<User>(userService.updateUser(newUser, id), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("api/users/{id}")
    ResponseEntity deleteEmployee(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
