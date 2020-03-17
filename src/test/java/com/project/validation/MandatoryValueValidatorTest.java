package com.project.validation;

import com.project.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class MandatoryValueValidatorTest {

    MandatoryValueValidator validator;

    @BeforeEach
    void setUp() {
        validator = new MandatoryValueValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaaaa","aaaa aaa", ") (", "a ", "   aaa    ", "fff fff fff "})
    void shouldPassWithValueGiven(String value) {
        assertDoesNotThrow(() -> {
            validator.validate(value, "Laukas");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {" ","         ", ""})
    void shouldFailWithBlank(String value) {
        assertThrows(ValidationException.class, () -> {
            validator.validate(value, "Laukas");
        });
    }

    @Test
    void shouldFailWithNull(){
        assertThrows(ValidationException.class, () -> {
            validator.validate(null, "Laukas");
        });
    }
}