package com.project.service;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.model.Application;
import com.project.model.ApplicationStatus;
import com.project.model.User;
import com.project.model.request.ApplicationRequest;
import com.project.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<Application> getAll() {
        return new ArrayList<>(applicationRepository.findAll());
    }

    @Override
    public Application getById(long id) throws ApplicationNotFoundException {
        return applicationRepository.findById(id).orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    @Override
    public Application addApplication(ApplicationRequest application) throws UserException {
        User user = userService.addUser(application.getUser());
        Application newApplication = new Application(
                application.getPhoneNumber(), application.getEducation(), application.getHobbies(),
                application.isAgreementNeeded(), application.getComment(), application.isAcademyTimeSuitable(),
                application.getReason(), application.getTechnologies(), application.getSource(), application.getApplicationDate(),
                ApplicationStatus.NAUJA, new ArrayList<>(), user);
        return applicationRepository.save(newApplication);
    }

    @Override
    public void deleteApplication(long id) throws ApplicationNotFoundException {
        if (!applicationRepository.findById(id).isPresent()) {
            throw new ApplicationNotFoundException(id);
        }
        applicationRepository.deleteById(id);
    }

    @Override
    public Application updateApplication(Application application, long id) throws ApplicationNotFoundException {
        Optional<Application> existingApplication = applicationRepository.findById(id);

        if (!existingApplication.isPresent()) {
            throw new ApplicationNotFoundException(id);
        }

        return existingApplication
                .map(existingApp -> {
                    existingApp.setStatus(application.getStatus());
                    return applicationRepository.save(existingApp);
                })
                .orElse(null);
    }
}