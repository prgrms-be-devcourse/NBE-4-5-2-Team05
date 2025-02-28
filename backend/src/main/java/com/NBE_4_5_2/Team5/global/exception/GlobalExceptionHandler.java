package com.NBE_4_5_2.Team5.global.exception;

import com.NBE_4_5_2.Team5.global.app.AppConfig;
import com.NBE_4_5_2.Team5.global.dto.RsData;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RsData<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        String message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + " : " + fe.getCode() + " : "  + fe.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining("\n"));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        new RsData<>(
                                "400-1",
                                message
                        )
                );
    }


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<RsData<Void>> ServiceExceptionHandle(ServiceException ex) {

        if(AppConfig.isNotProd()) ex.printStackTrace();

        return ResponseEntity
                .status(ex.getStatusCode())
                .body(
                        new RsData<>(
                                ex.getCode(),
                                ex.getMsg()
                        )
                );
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RsData<Void>> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RsData<>("404", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RsData<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RsData<>("400", e.getMessage()));
    }

}
