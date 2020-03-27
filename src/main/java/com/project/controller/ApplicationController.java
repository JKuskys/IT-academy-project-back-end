package com.project.controller;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.model.request.ApplicationRequest;
import com.project.model.request.ApplicationUpdateRequest;
import com.project.model.response.ApplicationResponse;
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
    public ResponseEntity<List<ApplicationResponse>> fetchApplications() {
        return new ResponseEntity<>(applicationService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> fetchApplication(@PathVariable Long id) throws ApplicationNotFoundException {
        return new ResponseEntity<>(new ApplicationResponse(applicationService.getById(id)), HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<ApplicationResponse> createApplication(@Valid @RequestBody ApplicationRequest application) throws UserException {
        return new ResponseEntity<>(applicationService.addApplication(application), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<ApplicationResponse> updateApplication(@RequestBody ApplicationUpdateRequest application, @PathVariable Long id) throws UserException, ApplicationNotFoundException {
        return new ResponseEntity<>(applicationService.updateApplication(application, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteApplication(@PathVariable Long id) throws ApplicationNotFoundException {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}