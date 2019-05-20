package com.sumhr.api.error;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomError methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<org.springframework.validation.FieldError> fieldErrors = result.getFieldErrors();
        List<CustomFieldError> errors = new ArrayList<>();
        fieldErrors.forEach(fieldError -> {
            errors.add(new CustomFieldError(fieldError.getField(), fieldError.getCode(), fieldError.getDefaultMessage()));
        });
        CustomError error = new CustomError(CustomErrorType.FIELD_ERROR, ex.getClass().getName(), new Date(), errors);
        return error;
    }

    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ResponseBody
    @ExceptionHandler(UnsupportedOperationException.class)
    public CustomError handleUnsupportedOperationException(UnsupportedOperationException ex) {
        CustomError errorDTO = new CustomError(CustomErrorType.NOT_IMPLEMENTED_ERROR, ex.getMessage(), new Date(), null);
        return errorDTO;
    }

//    @ResponseStatus(HttpStatus.CONFLICT)
//    @ResponseBody
//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public CustomError handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//        CustomError errorDTO = new CustomError(CustomErrorType.DATA_INTEGRITY_ERROR, ex.getMessage(), new Date(), null);
//        return errorDTO;
//    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    @ExceptionHandler(AssertionError.class)
    public CustomError handleAssertionError(AssertionError ex) {
        CustomError errorDTO = new CustomError(CustomErrorType.LOGICAL_ERROR, ex.getMessage(), new Date(), null);
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    @ExceptionHandler(CustomResourceNotFoundException.class)
    public CustomError handleTenoResourceNotFoundException(CustomResourceNotFoundException ex) {
        CustomError errorDTO = new CustomError(CustomErrorType.NOT_FOUND_ERROR, ex.getMessage(), new Date(), null);
        return errorDTO;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public CustomError handleNullPointerException(NullPointerException ex) {
        log.error("~ handleNullPointerException ~ {}", ex);
        CustomError errorDTO = new CustomError(CustomErrorType.SERVER_ERROR, ex.getMessage(), new Date(), null);
        return errorDTO;
    }

}
