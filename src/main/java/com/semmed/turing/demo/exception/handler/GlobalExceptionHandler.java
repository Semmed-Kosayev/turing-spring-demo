package com.semmed.turing.demo.exception.handler;

import com.semmed.turing.demo.exception.AlreadyExistsException;
import com.semmed.turing.demo.exception.InvalidInputException;
import com.semmed.turing.demo.exception.NotFoundException;
import com.semmed.turing.demo.model.constants.ErrorCode;
import com.semmed.turing.demo.model.dto.GlobalErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<GlobalErrorResponse> handleAlreadyExistsException(AlreadyExistsException e) {
        logError(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GlobalErrorResponse(
                        UUID.randomUUID(),
                        ErrorCode.ALREADY_EXISTS,
                        e.getMessage(),
                        LocalDateTime.now())
                );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<GlobalErrorResponse> handleNotFoundException(NotFoundException e) {
        logError(e);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new GlobalErrorResponse(
                        UUID.randomUUID(),
                        ErrorCode.NOT_FOUND,
                        e.getMessage(),
                        LocalDateTime.now())
                );
    }


    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<GlobalErrorResponse> handleInvalidInputException(InvalidInputException e) {
        logError(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GlobalErrorResponse(
                        UUID.randomUUID(),
                        ErrorCode.INVALID_INPUT,
                        e.getMessage(),
                        LocalDateTime.now())
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<GlobalErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        logError(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GlobalErrorResponse(
                        UUID.randomUUID(),
                        ErrorCode.INVALID_INPUT,
                        e.getMessage(),
                        LocalDateTime.now())
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logError(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GlobalErrorResponse(
                        UUID.randomUUID(),
                        ErrorCode.INVALID_INPUT,
                        e.getMessage(),
                        LocalDateTime.now())
                );
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<GlobalErrorResponse> handleBindException(BindException e) {
        logError(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GlobalErrorResponse(
                        UUID.randomUUID(),
                        ErrorCode.INVALID_INPUT,
                        e.getMessage(),
                        LocalDateTime.now())
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        logError(e);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new GlobalErrorResponse(
                        UUID.randomUUID(),
                        ErrorCode.INVALID_INPUT,
                        e.getMessage(),
                        LocalDateTime.now())
                );
    }

    public void logError(Exception e) {
        log.error("{} happened: {}", e.getClass().getSimpleName(), e.getMessage());
    }
}
