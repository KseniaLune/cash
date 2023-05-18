package com.example.cash.web.controller;

import com.example.cash.domain.exception.AccessDeniedEx;
import com.example.cash.domain.exception.ExBody;
import com.example.cash.domain.exception.ResourceMappingEx;
import com.example.cash.domain.exception.ResourceNotFoundEx;
import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ResourceNotFoundEx.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExBody handleResourceNotFound(ResourceNotFoundEx ex) {
        return new ExBody(ex.getMessage());
    }

    @ExceptionHandler(ResourceMappingEx.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExBody handleResourceMapping(ResourceMappingEx ex) {
        return new ExBody(ex.getMessage());
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExBody handleIllegalState(IllegalStateException ex) {
        return new ExBody(ex.getMessage());
    }

    @ExceptionHandler({AccessDeniedEx.class, org.springframework.security.access.AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExBody handleAccessDenied() {
        return new ExBody("Access denied.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExBody handlerMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ExBody exBody = new ExBody("Validation failed.");
        List<FieldError> errors = ex.getBindingResult().getFieldErrors();
        exBody.setErrors(errors.stream()
            .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));

        return exBody;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExBody handleConstraintViolation(ConstraintViolationException ex) {
        ExBody exBody = new ExBody("Validation failed.");
        exBody.setErrors(ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                violation -> violation.getMessage()
            )));
        return exBody;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExBody handleException(Exception ex){
        return new ExBody("Internal error.");
    }
}
