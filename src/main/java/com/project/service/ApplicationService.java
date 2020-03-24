package com.project.service;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.model.Application;
import com.project.model.request.ApplicationRequest;

import java.util.List;

public interface ApplicationService {
    List<Application> getAll();

    Application getById(long id) throws ApplicationNotFoundException;

    Application addApplication(ApplicationRequest application) throws UserException;

    void deleteApplication(long id) throws ApplicationNotFoundException;

    Application updateApplication(Application application, long id) throws ApplicationNotFoundException;
}
