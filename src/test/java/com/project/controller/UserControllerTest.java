package com.project.controller;

import com.project.model.Application;
import com.project.model.ApplicationStatus;
import com.project.model.User;
import com.project.model.request.UserApplicationRequest;
import com.project.model.request.UserRequest;
import com.project.model.response.UserResponse;
import com.project.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private User user;

    @Mock
    private UserRequest userRequest;

    @Mock
    private Application application;

    @Mock
    private UserApplicationRequest userAppRequest;

    @Mock
    private UserResponse userResponse;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(user.getEmail()).thenReturn("test@email.com");
        when(user.getFullName()).thenReturn("test name");
    }

    @Test
    public void shouldSucceedInFetchingUser() throws Exception {
        when(userService.getById(1L)).thenReturn(user);

        ResponseEntity response = userController.fetchUser(1L);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldSucceedInFetchingUserApplication() throws Exception {
        when(userService.getByEmail(any(String.class))).thenReturn(user);
        when(user.getApplication()).thenReturn(application);
        when(userAppRequest.getEmail()).thenReturn("test@email.com");

        when(application.getApplicant()).thenReturn(user);
        when(application.getId()).thenReturn(1L);
        when(application.getApplicant().getFullName()).thenReturn("test name");
        when(application.getPhoneNumber()).thenReturn("test number");
        when(application.getEducation()).thenReturn("test education");
        when(application.getHobbies()).thenReturn("test hobbies");
        when(application.isAgreementNeeded()).thenReturn(true);
        when(application.getComment()).thenReturn("");
        when(application.isAcademyTimeSuitable()).thenReturn(true);
        when(application.getReason()).thenReturn("test reason");
        when(application.getTechnologies()).thenReturn("test technologies");
        when(application.getSource()).thenReturn("test source");
        when(application.getApplicationDate()).thenReturn("2020-12-20");
        when(application.getApplicant().getEmail()).thenReturn("test@email.com");
        when(application.getStatus()).thenReturn(ApplicationStatus.NAUJA);

        ResponseEntity response = userController.fetchUserApplication(userAppRequest);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldSucceedInCreatingUser() throws Exception {
        when(userService.addUser(userRequest)).thenReturn(user);

        ResponseEntity response = userController.createUser(userRequest);

        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void shouldSucceedInUpdatingUser() throws Exception {
        when(userService.updateUser(userRequest, 1L)).thenReturn(userResponse);

        ResponseEntity response = userController.updateUser(userRequest, 1L);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void shouldSucceedInDeletingUser() throws Exception {
        ResponseEntity response = userController.deleteUser(1L);

        Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

}
