package com.project.validation;

import com.project.exception.ValidationException;
import com.project.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserValidatorTest {
    @Mock
    EmailValidator emailValidator;
    @Mock
    MandatoryValueValidator mandatoryValueValidator;
    @Mock
    PasswordValidator passwordValidator;

    @InjectMocks
    UserValidator validator;

    User testUser;

    @BeforeEach
    void setUp() {
        initMocks(this);

        initTestUser();
    }

    private void initTestUser(){
        testUser = new User();
        testUser.setPassword("A1asdasd");
        testUser.setEmail("aaaaaa@aaa.aa");
    }

    @Test
    void shouldPassWithValidUser() {

        assertDoesNotThrow(() -> {
            validator.validate(testUser);
        });

        verify(mandatoryValueValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");
        verify(emailValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");

        verify(mandatoryValueValidator, times(1)).validate(testUser.getPassword(), "Slaptažodis");
        verify(passwordValidator, times(1)).validate(testUser.getPassword(), "Slaptažodis");
    }

    @Test
    void shouldFailWithUserWithNoEmail() {
        doThrow(ValidationException.class).when(mandatoryValueValidator).validate(testUser.getEmail(), "Elektroninis paštas");

        assertThrows(ValidationException.class, () -> {
            validator.validate(testUser);
        });

        verify(mandatoryValueValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");
    }

    @Test
    void shouldFailWithUserWithInvalidEmail() {
        doThrow(ValidationException.class).when(emailValidator).validate(testUser.getEmail(), "Elektroninis paštas");

        assertThrows(ValidationException.class, () -> {
            validator.validate(testUser);
        });

        verify(mandatoryValueValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");
        verify(emailValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");
    }

    @Test
    void shouldFailWithUserWithNoPassword() {
        doThrow(ValidationException.class).when(mandatoryValueValidator).validate(testUser.getPassword(), "Slaptažodis");

        assertThrows(ValidationException.class, () -> {
            validator.validate(testUser);
        });

        verify(mandatoryValueValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");
        verify(emailValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");

        verify(mandatoryValueValidator, times(1)).validate(testUser.getPassword(), "Slaptažodis");
    }

    @Test
    void shouldFailWithUserWithInvalidPassword() {
        doThrow(ValidationException.class).when(passwordValidator).validate(testUser.getPassword(), "Slaptažodis");

        assertThrows(ValidationException.class, () -> {
            validator.validate(testUser);
        });

        verify(mandatoryValueValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");
        verify(emailValidator, times(1)).validate(testUser.getEmail(), "Elektroninis paštas");

        verify(mandatoryValueValidator, times(1)).validate(testUser.getPassword(), "Slaptažodis");
        verify(passwordValidator, times(1)).validate(testUser.getPassword(), "Slaptažodis");
    }
}