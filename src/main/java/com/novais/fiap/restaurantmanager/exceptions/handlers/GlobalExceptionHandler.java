package com.novais.fiap.restaurantmanager.exceptions.handlers;

import com.novais.fiap.restaurantmanager.exceptions.InsertToDatabaseException;
import com.novais.fiap.restaurantmanager.exceptions.InvalidCredentialsException;
import com.novais.fiap.restaurantmanager.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setType(URI.create("input-field-validation"));
        problem.setTitle("Erro de validação");

        problem.setProperty("errors",
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(e -> Map.of(
                                "field", e.getField(),
                                "message", e.getDefaultMessage()
                        ))
                        .toList()
        );

        problem.setDetail("Um ou mais campos inválidos, checar erros");
        return problem;
    }

    @ExceptionHandler(InsertToDatabaseException.class)
    public ProblemDetail handleDataBaseEx(InsertToDatabaseException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);

        problem.setType(URI.create("insert-db-error"));
        problem.setTitle("Problema para salvar no banco de dados");
        problem.setDetail(ex.getMessage());

        return problem;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ProblemDetail handleInvalidCredentials(InvalidCredentialsException ex) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);

        problem.setType(URI.create("invalid-credentials"));
        problem.setTitle("Credenciais inválidas");
        problem.setDetail(ex.getMessage());

        return problem;
    }


}
