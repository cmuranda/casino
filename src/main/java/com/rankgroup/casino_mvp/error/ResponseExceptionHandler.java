package com.rankgroup.casino_mvp.error;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseStatus
public class ResponseExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorMessage> playerNotFoundException(
            PlayerNotFoundException playerNotFoundException,
            WebRequest webRequest
    ){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                playerNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<ErrorMessage> invalidAmountException(
            InvalidAmountException exception,
            WebRequest webRequest
    ){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.BAD_REQUEST,
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(InvalidWagerException.class)
    public ResponseEntity<ErrorMessage> invalidWagerException(
            InvalidWagerException exception,
            WebRequest webRequest
    ){
        ErrorMessage errorMessage = new ErrorMessage(HttpStatus.I_AM_A_TEAPOT,
                exception.getMessage());
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(errorMessage);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDatabaseError(
            DataIntegrityViolationException exception,
            WebRequest request
    ){
        String error = exception.getMessage();

        ErrorMessage errorMessages = new ErrorMessage(HttpStatus.BAD_REQUEST, error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                               HttpHeaders headers,
                                                               HttpStatusCode status,
                                                               WebRequest request){
        List<String> errorList = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorMessages errorMessages = new ErrorMessages(HttpStatus.BAD_REQUEST, errorList);
        return handleExceptionInternal(exception, errorMessages, headers, HttpStatus.BAD_REQUEST, request);
    }
}
