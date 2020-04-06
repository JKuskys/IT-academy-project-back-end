package com.project.service;

import com.project.exception.ApplicationNotFoundException;
import com.project.model.Application;
import com.project.model.ApplicationStatus;
import com.project.model.User;
import com.project.model.request.ApplicationRequest;
import com.project.model.request.UserRequest;
import com.project.model.response.ApplicationResponse;
import com.project.repository.ApplicationRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ApplicationServiceTest {

    @InjectMocks
    private ApplicationServiceImpl applicationService;

    @Mock
    private Application application;

    @Mock
    private User user;

    @Mock
    private UserRequest userRequest;

    @Mock
    private ApplicationRequest applicationRequest;

    @Mock
    private ApplicationRepository applicationRepository;

    @Mock
    private UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldSucceedInGettingApplication() throws Exception {
        //given
        when(applicationRepository.findById(1L)).thenReturn(Optional.of(application));

        //when
        Application response = applicationService.getById(1L);

        //then
        Assert.assertNotNull(response);
    }

    @Test(expected = ApplicationNotFoundException.class)
    public void shouldThrowExceptionWhenApplicationDoesNotExist() throws Exception {
        //given
        when(applicationRepository.findById(10L)).thenThrow(ApplicationNotFoundException.class);

        //when
        applicationService.getById(10L);
    }

    @Test
    public void shouldSucceedInSavingApplication() throws Exception {
        //given
        when(applicationRequest.getUser()).thenReturn(userRequest);
        when(userService.addUser(userRequest)).thenReturn(user);
        when(applicationRepository.save(any(Application.class))).thenReturn(application);

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

        //when
        ApplicationResponse response = applicationService.addApplication(applicationRequest);

        //then
        Assert.assertEquals(Long.valueOf(1), response.getId());
        Assert.assertEquals("test name", response.getFullName());
        Assert.assertEquals("test number", response.getPhoneNumber());
        Assert.assertEquals("test education", response.getEducation());
        Assert.assertEquals("test hobbies", response.getHobbies());
        Assert.assertTrue(response.isAgreementNeeded());
        Assert.assertEquals("", response.getComment());
        Assert.assertTrue(response.isAcademyTimeSuitable());
        Assert.assertEquals("test reason", response.getReason());
        Assert.assertEquals("test technologies", response.getTechnologies());
        Assert.assertEquals("test source", response.getSource());
        Assert.assertEquals("2020-12-20", response.getApplicationDate());
        Assert.assertEquals("test@email.com", response.getEmail());
        Assert.assertEquals(ApplicationStatus.NAUJA, response.getStatus());
    }
}
