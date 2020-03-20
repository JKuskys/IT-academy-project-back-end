package com.project.service;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.Application;
import com.project.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
    public Application addApplication(Application application) throws UserException {
        userService.addUser(application.getUser());
        application.setId(null);//do not allow choosing id
        return applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(long id) throws ApplicationNotFoundException {
        if (!applicationRepository.findById(id).isPresent())
            throw new ApplicationNotFoundException(id);
        applicationRepository.deleteById(id);
    }

    @Override
    public Application updateApplication(Application application, long id) throws ApplicationNotFoundException {
        if (!applicationRepository.findById(id).isPresent())
            throw new ApplicationNotFoundException(id);

        return applicationRepository.findById(id)
                .map(existingApplication -> {
                    //TODO add status update ONLY (with admin actions)
                    return applicationRepository.save(existingApplication);
                })
                .orElse(null);
    }
}