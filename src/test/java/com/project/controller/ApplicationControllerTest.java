package com.project.controller;

import com.project.model.Application;
import com.project.model.User;
import com.project.model.request.ApplicationRequest;
import com.project.model.request.ApplicationUpdateRequest;
import com.project.model.response.ApplicationResponse;
import com.project.service.ApplicationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.when;

public class ApplicationControllerTest {

    @InjectMocks
    private ApplicationController applicationController;

    @Mock
    private ApplicationService applicationService;

    @Mock
    private Application application;

    @Mock
    private ApplicationResponse applicationResponse;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(applicationService.getById(1L)).thenReturn(application);
        when(applicationService.addApplication(new ApplicationRequest())).thenReturn(applicationResponse);
        when(applicationService.updateApplication(new ApplicationUpdateRequest(), 1L)).thenReturn(applicationResponse);
        when(application.getApplicant()).thenReturn(new User());
    }

    @Test
    public void shouldSucceedInFetchingApplication() throws Exception {
        ResponseEntity<ApplicationResponse> app = applicationController.fetchApplication(1L);

        Assert.assertEquals(HttpStatus.OK, app.getStatusCode());
    }

    @Test
    public void shouldSucceedInCreatingApplication() throws Exception {
        ResponseEntity<ApplicationResponse> app = applicationController.createApplication(new ApplicationRequest());

        Assert.assertEquals(HttpStatus.CREATED, app.getStatusCode());
    }

    @Test
    public void shouldSucceedInUpdatingApplication() throws Exception {
        ResponseEntity<ApplicationResponse> app = applicationController.updateApplication(new ApplicationUpdateRequest(), 1L);

        Assert.assertEquals(HttpStatus.OK, app.getStatusCode());
    }

    @Test
    public void shouldSucceedInDeletingApplication() throws Exception {
        ResponseEntity app = applicationController.deleteApplication(1L);

        Assert.assertEquals(HttpStatus.NO_CONTENT, app.getStatusCode());
    }
}
