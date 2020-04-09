package com.project.service;

import com.project.exception.UserNotFoundException;
import com.project.model.User;
import com.project.model.request.UserRequest;
import com.project.repository.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private User user;

    @Mock
    private BCryptPasswordEncoder bcryptEncoder;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void shouldSucceedInGettingUserById() throws Exception {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User response = userService.getById(1L);

        Assert.assertNotNull(response);
    }

    @Test
    public void shouldSucceedInGettingUserByEmail() {
        when(userRepository.findByEmail("test@email.com")).thenReturn(Optional.of(user));

        User response = userService.getByEmail("test@email.com");

        Assert.assertNotNull(response);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExceptionWhenUserIdDoesNotExist() throws Exception {
        when(userRepository.findById(10L)).thenThrow(UserNotFoundException.class);

        userService.getById(10L);
    }

    @Test(expected = UsernameNotFoundException.class)
    public void shouldThrowExceptionWhenUserEmailDoesNotExist() {
        when(userRepository.findByEmail("fake.email")).thenThrow(UsernameNotFoundException.class);

        userService.getByEmail("fake.email");
    }

    @Test
    public void shouldSucceedInSavingUser() throws Exception {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.empty());
        UserRequest request = new UserRequest("test@email.com", "testPassword123", "test name");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(bcryptEncoder.encode(any(String.class))).thenReturn("encryptedPassword123");

        User response = userService.addUser(request);

        Assert.assertNotNull(response);
    }
}
