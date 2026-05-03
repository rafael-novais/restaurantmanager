package com.novais.fiap.restaurantmanager.exceptions.handlers;

import com.novais.fiap.restaurantmanager.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleUserNotFound(ResourceNotFoundException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

        problem.setType(URI.create("resource-not-found"));
        problem.setTitle("Recurso não encontrado");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleAuthError(BadCredentialsException ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);

        problem.setType(URI.create("invalid-credentials"));
        problem.setTitle("Credenciais inválidas");
        problem.setDetail("Email ou senha incorretos");

        return problem;
    }
}
