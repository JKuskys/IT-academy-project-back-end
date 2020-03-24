package com.project.controller;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.model.Application;
import com.project.model.response.ApplicationResponse;
import com.project.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
        List<Application> applications = applicationService.getAll();
        List<ApplicationResponse> response = new ArrayList<>();

        for (Application app : applications) {
            response.add(new ApplicationResponse(
                    app.getId(), app.getFullName(), app.getPhoneNumber(), app.getEducation(), app.getHobbies(), app.isAgreementNeeded(),
                    app.getComment(), app.isAcademyTimeSuitable(), app.getReason(), app.getTechnologies(), app.getSource(), app.getApplicationDate(),
                    app.getUser().getEmail(), app.getComments().size(), app.getStatus())
            );
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> fetchApplication(@PathVariable long id) throws ApplicationNotFoundException {
        Application app = applicationService.getById(id);
        ApplicationResponse response = new ApplicationResponse(
                app.getId(), app.getFullName(), app.getPhoneNumber(), app.getEducation(), app.getHobbies(), app.isAgreementNeeded(),
                app.getComment(), app.isAcademyTimeSuitable(), app.getReason(), app.getTechnologies(), app.getSource(), app.getApplicationDate(),
                app.getUser().getEmail(), app.getComments().size(), app.getStatus());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<HttpStatus> createApplication(@Valid @RequestBody Application application) throws UserException, ApplicationNotFoundException {
        applicationService.addApplication(application);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateApplication(@RequestBody Application application, @PathVariable Long id) throws UserException, ApplicationNotFoundException {
        applicationService.updateApplication(application, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteApplication(@PathVariable Long id) throws ApplicationNotFoundException {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }
}