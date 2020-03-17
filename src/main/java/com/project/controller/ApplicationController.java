package com.project.controller;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.model.Application;
import com.project.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ApplicationController {
    private ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("api/applications")
    public ResponseEntity<List<Application>> getAll() {
        List<Application> list = applicationService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("api/applications/{id}")
    public ResponseEntity<Application> getApplication(@PathVariable long id) throws ApplicationNotFoundException {
        return new ResponseEntity<Application>(applicationService.getById(id), HttpStatus.OK);
    }

    @PostMapping("api/applications")
    ResponseEntity<Application> postUser(@Valid @RequestBody Application newApplication) throws UserException, ApplicationNotFoundException {
        applicationService.addApplication(newApplication);
        return new ResponseEntity<Application>(applicationService.getById(newApplication.getId()), HttpStatus.CREATED);
    }

    @PutMapping("api/applications/{id}")
    ResponseEntity<Application> putApplication(@Valid @RequestBody Application newApplication, @PathVariable Long id) throws UserException, ApplicationNotFoundException {
        return new ResponseEntity<Application>(applicationService.updateApplication(newApplication, id), HttpStatus.OK);
    }

    @DeleteMapping("api/applications/{id}")
    ResponseEntity<Application> deleteApplication(@PathVariable Long id) throws ApplicationNotFoundException {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}