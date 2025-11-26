package com.demo.fulfillment_service.exception;

import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private ApiError build(HttpStatus status, String message) {
        return new ApiError(
                status.value(),
                message,
                LocalDateTime.now().toString()
        );
    }

    // 1. Specific exception for insufficient inventory
    @ExceptionHandler(InsufficientInventoryException.class)
    public ResponseEntity<ApiError> handleInsufficientInventory(
            InsufficientInventoryException exception) {

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(build(HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage()));
    }

    // 2. Optimistic locking (concurrency conflict)
    @ExceptionHandler(OptimisticLockException.class)
    public ResponseEntity<ApiError> handleOptimisticLockException(
            OptimisticLockException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(build(HttpStatus.CONFLICT,
                        "Inventory was modified by another request. Please retry."));
    }

    // 3. Validation exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException exception) {

        String message = exception
                .getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST, message));
    }

    // 4. Illegal arguments (bad request)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgumentException(
            IllegalArgumentException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST, exception.getMessage()));
    }

    // 5. Fallback for unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception exception) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(build(HttpStatus.INTERNAL_SERVER_ERROR,
                        "An unexpected error occurred. Please try again."));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleJsonParsingError(HttpMessageNotReadableException exception) {
        Throwable rootCause = exception.getMostSpecificCause();

        String message = "Malformed JSON request";

        if (rootCause != null) {
            message = rootCause.getMessage();
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(build(HttpStatus.BAD_REQUEST, message));
    }

}
