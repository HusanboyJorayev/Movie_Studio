package com.example.movie_studio.exceptions;

import com.example.movie_studio.dto.ErrorDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.function.BiFunction;

@RestControllerAdvice
public class ExceptionClass {
    @ExceptionHandler
    public ResponseEntity<ErrorDto> methodNotValid(MethodArgumentNotValidException e, HttpServletRequest r) {
        Map<String, List<String>> errorBody = new HashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            String field = fieldError.getField();
            String message = fieldError.getDefaultMessage();
            errorBody.compute(field, new BiFunction<>() {
                @Override
                public List<String> apply(String s, List<String> strings) {
                    strings = Objects.requireNonNullElse(strings, new ArrayList<>());
                    strings.add(message);
                    return strings;
                }
            });
        }
        return ResponseEntity.status(404)
                .body(ErrorDto.builder()
                        .path(r.getRequestURI())
                        .code(404)
                        .errorBody(errorBody)
                        .build());
    }
}
