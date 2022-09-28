package com.huhoot.exception;

import com.huhoot.config.mvc.CustomRestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.auth.login.AccountNotFoundException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler({LockedException.class, BadCredentialsException.class, CustomException.class})
    public CustomRestResponse handleAuthenticationException(Exception e) {
        log.debug("handleAuthenticationException" + e.getMessage());
        return CustomRestResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({NoSuchElementException.class})
    public CustomRestResponse nullPointerException(Exception e) {
        return CustomRestResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler({AccountNotFoundException.class, NotYourOwnException.class, ChallengeException.class, StudentAddException.class, UsernameExistedException.class, AccountException.class})
    public CustomRestResponse handleServiceException(Exception e) {
        return CustomRestResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CustomRestResponse handleMethodArgumentNotValidException(Exception e) {
        log.error(e.getMessage());
        return CustomRestResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(Exception.class)
    public CustomRestResponse handleUnwantedException(Exception e) {
        log.error(e.getMessage());
        return CustomRestResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
    }
}