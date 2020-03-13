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
}
