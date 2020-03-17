package com.project.validation;

import com.project.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class PasswordValidatorTest {

    private PasswordValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PasswordValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaA1aa","aaaaaaaaasaaaaaaaaasaaaaaaaaA1", "))))A1aaa", "asdASD123", "===!a1A", "11asdAA"})
    void shouldPassWithValidPassword(String password) {
        assertDoesNotThrow(() -> {
            validator.validate(password, "Slaptažodis");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaA1a","aaaaaaaaasaaaaaaaaasaaaaaaaaaA1"})
    void shouldFailWithInvalidLength(String password) {
        assertThrows(ValidationException.class, () -> {
            validator.validate(password, "Slaptažodis");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa A1a","aaaaaafff&", " ", "", "aaaaaaaaaaaaa", "aaaaaa111111", "AAAAAAAAAA", "11111111"})
    void shouldFailWithInvalidStructure(String password) {
        assertThrows(ValidationException.class, () -> {
            validator.validate(password, "Slaptažodis");
        });
    }
}