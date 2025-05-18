package it.fulminazzo.userstalker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception.getFieldErrors().stream().findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("");
        return generateErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(HttpRequestException.class)
    public ResponseEntity<?> handleHttpRequestException(HttpRequestException exception) {
        return generateErrorResponse(exception.getStatus(), exception.getMessage());
    }

    private ResponseEntity<ErrorResponse> generateErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message));
    }

    public record ErrorResponse(int status, String error) {
    }

}
