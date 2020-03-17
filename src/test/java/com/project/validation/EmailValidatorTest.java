package com.project.validation;

import com.project.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    private EmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new EmailValidator();
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaa@aa.aa","AAAAAA@AAAA.AA","a@asd.aa",
            "123345@asd.com","asd213@co.uk", "123-345@asd.com", "123.345@asd.com"})
    void shouldPassWithValidEmail(String email) {
        assertDoesNotThrow(() -> {
            validator.validate(email, "Elektroninis paštas");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaaaa","AAAAAA","asdasd.asd","123345asd.com@com", "@@@@@@.com", "as@d@s.ss", "@aazaaa.aa"})
    void shouldFailWithInvalidAtSign(String email) {
        assertThrows(ValidationException.class, () -> {
            validator.validate(email, "Elektroninis paštas");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"aa*a@aa.aa","aaa@aa.a^a","aaa@a#a.aa","*@aa.aa","asd@aa.aa*"})
    void shouldFailWithInvalidSymbols(String email) {
        assertThrows(ValidationException.class, () -> {
            validator.validate(email, "Elektroninis paštas");
        });
    }

    @ParameterizedTest
    @ValueSource(strings = {"aaaaaaa@aa.a","aaa@.ag","@aaa.aa", ".@aaa.aa", "asd aa@sd.com", ""})
    void shouldFailWithInvalidGeneralStructure(String email) {
        assertThrows(ValidationException.class, () -> {
            validator.validate(email, "Elektroninis paštas");
        });
    }
}