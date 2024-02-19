package com.transferz.exception;

import com.transferz.dto.GenericErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final GenericErrorResponse errorResponse;

    @ExceptionHandler({PassengerNotFoundException.class, AirportNotFoundException.class, FlightNotFoundException.class})
    public ResponseEntity<GenericErrorResponse> handleNotFoundException(Exception e) {
        errorResponse.setErrorCode(HttpStatus.NOT_FOUND);
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PassengerAlreadyExistException.class, AirportAlreadyExistException.class})
    public ResponseEntity<GenericErrorResponse> handlePassengerExistException(Exception e) {
        errorResponse.setErrorCode(HttpStatus.CONFLICT);
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<GenericErrorResponse> handleIntegrityException(DataIntegrityViolationException e) {
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMessage(Objects.requireNonNull(Objects.requireNonNull(e.getRootCause()).getMessage()));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<GenericErrorResponse> handleNotNullException(MethodArgumentNotValidException e) {
        errorResponse.setErrorCode(HttpStatus.BAD_REQUEST);
        errorResponse.setErrorMessage(Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class, DatabaseException.class})
    public ResponseEntity<GenericErrorResponse> handleException(Exception e) {
        errorResponse.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR);
        errorResponse.setErrorMessage(e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
