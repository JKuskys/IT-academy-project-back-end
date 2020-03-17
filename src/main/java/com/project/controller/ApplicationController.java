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
@RequestMapping("api/applications")
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<Application>> getAll() {
        List<Application> list = applicationService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Application> fetchApplication(@PathVariable long id) throws ApplicationNotFoundException {
        return new ResponseEntity<>(applicationService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Application> createApplication(@Valid @RequestBody Application newApplication) throws UserException, ApplicationNotFoundException {
        Application savedApplication = applicationService.addApplication(newApplication);
        return new ResponseEntity<>(savedApplication, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<Application> updateApplication(@RequestBody Application changedApplication, @PathVariable Long id) throws UserException, ApplicationNotFoundException {
        return new ResponseEntity<>(applicationService.updateApplication(changedApplication, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteApplication(@PathVariable Long id) throws ApplicationNotFoundException {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}