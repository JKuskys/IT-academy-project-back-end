package com.project.service;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserException;
import com.project.model.Application;
import com.project.model.request.ApplicationRequest;
import com.project.model.request.ApplicationUpdateRequest;
import com.project.model.response.ApplicationResponse;

import java.util.List;

public interface ApplicationService {
    List<ApplicationResponse> getAll();

    Application getById(Long id) throws ApplicationNotFoundException;

    ApplicationResponse addApplication(ApplicationRequest application) throws UserException;

    void deleteApplication(Long id) throws ApplicationNotFoundException;

    ApplicationResponse updateApplication(ApplicationUpdateRequest application, Long id) throws ApplicationNotFoundException;
}
