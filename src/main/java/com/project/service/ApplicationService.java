package com.project.service;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.exception.UserNotFoundException;
import com.project.model.Application;

import java.util.List;

public interface ApplicationService {
    List<Application> getAll();
    Application getById(long id) throws ApplicationNotFoundException;
    void addApplication(Application application) throws UserException;
    void deleteApplication(long id) throws ApplicationNotFoundException;
    Application updateApplication(Application application, long id) throws ApplicationNotFoundException;
}
