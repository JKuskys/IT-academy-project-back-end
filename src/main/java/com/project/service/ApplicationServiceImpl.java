package com.project.service;

import com.project.exception.ApplicationNotFoundException;
import com.project.exception.UserNotFoundException;
import com.project.model.Application;
import com.project.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
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
    public void addApplication(Application application) {
        applicationRepository.save(application);
    }

    @Override
    public void deleteApplication(long id) throws ApplicationNotFoundException {
        if(!applicationRepository.findById(id).isPresent())
            throw new ApplicationNotFoundException(id);
        applicationRepository.deleteById(id);
    }

    @Override
    public Application updateApplication(Application application, long id) throws ApplicationNotFoundException {
        if(!applicationRepository.findById(id).isPresent())
            throw new ApplicationNotFoundException(id);
        return applicationRepository.findById(id)
                .map(existingApplication -> {
                    existingApplication.setAcademy_time(application.isAcademy_time());
                    existingApplication.setName(application.getName());
                    existingApplication.setLast_name(application.getLast_name());
                    existingApplication.setPhone_number(application.getPhone_number());
                    existingApplication.setEducation(application.getEducation());
                    existingApplication.setFree_time(application.getFree_time());
                    existingApplication.setAgreement(application.isAgreement());
                    existingApplication.setComment(application.getComment());
                    existingApplication.setReason(application.getReason());
                    existingApplication.setTechnologies(application.getTechnologies());
                    existingApplication.setSource(application.getSource());
                    existingApplication.setApplication_date(application.getApplication_date());
                    return applicationRepository.save(existingApplication);
                })
                .orElse(null);
    }
}