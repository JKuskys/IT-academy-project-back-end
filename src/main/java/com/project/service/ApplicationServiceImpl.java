package com.project.service;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.model.Application;
import com.project.model.ApplicationStatus;
import com.project.model.User;
import com.project.model.request.ApplicationRequest;
import com.project.model.request.ApplicationUpdateRequest;
import com.project.model.response.ApplicationResponse;
import com.project.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final UserService userService;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository, UserService userService) {
        this.applicationRepository = applicationRepository;
        this.userService = userService;
    }

    @Override
    public List<ApplicationResponse> getAll() {
        List<Application> applications = applicationRepository.findAll();
        return applications.stream().map(ApplicationResponse::new).collect(Collectors.toList());
    }

    @Override
    public Application getById(Long id) throws ApplicationNotFoundException {
        Application app = applicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(id));
        app.setNewInternalComment(false);
        app.setNewExternalComment(false);
        return applicationRepository.save(app);
    }

    @Override
    public ApplicationResponse addApplication(ApplicationRequest application) throws UserException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        User user = userService.addUser(application.getUser());
        Application newApplication = new Application(application, ApplicationStatus.NAUJA, new ArrayList<>(), user, dateFormat.format(new Date()));
        return new ApplicationResponse(applicationRepository.save(newApplication));
    }

    @Override
    public ApplicationResponse updateApplication(ApplicationUpdateRequest application, Long id) throws ApplicationNotFoundException {
        Optional<Application> existingApplication = applicationRepository.findById(id);

        if (!existingApplication.isPresent()) {
            throw new ApplicationNotFoundException(id);
        }

        Application app = existingApplication.map(existingApp -> {
            existingApp.setStatus(application.getStatus());
            return applicationRepository.save(existingApp);
        }).get();

        return new ApplicationResponse(app);
    }

    @Override
    public void deleteApplication(Long id) throws ApplicationNotFoundException {
        if (!applicationRepository.existsById(id)) {
            throw new ApplicationNotFoundException(id);
        }
        applicationRepository.deleteById(id);
    }
}