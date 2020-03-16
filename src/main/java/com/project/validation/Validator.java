package com.project.validation;

public abstract class Validator<T> {
    public abstract void validate(T attribute, String message);
}
