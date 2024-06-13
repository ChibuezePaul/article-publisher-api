package io.isoft.article.publisher.exception;

import io.isoft.article.publisher.models.dto.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiErrorResponse> handleBadCredentialException(BadCredentialsException ex) {
        ApiErrorResponse message = new ApiErrorResponse("User name or Password is incorrect");
        log.error("Bad Credentials Exception Occurred : {}", message);
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiErrorResponse> handleCustomException(CustomException ex) {
        ApiErrorResponse message = new ApiErrorResponse(ex.getMessage());
        log.error("Custom Exception Occurred : {}", message);
        return ResponseEntity.status(ex.getHttpStatus()).body(message);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        ApiErrorResponse message = new ApiErrorResponse(ex.getMessage());
        log.error("Username Not Found Exception Occurred : {}", message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiErrorResponse> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        ApiErrorResponse message = new ApiErrorResponse(ex.getMessage());
        log.error("Insufficient Authentication Exception Occurred : {}", message);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("Method Argument Not Valid Exception Occurred : {}", message);
        return ResponseEntity.badRequest().body(new ApiErrorResponse(message));
    }
}
