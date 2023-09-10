package ru.netology.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ErrorInputData.class)
    public ResponseEntity<ErrorInputData> eidHandler(ErrorInputData e) {
        return new ResponseEntity<ErrorInputData>(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorTransfer.class)
    public ResponseEntity<String> etHandler(ErrorTransfer e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorConfirmation.class)
    public ResponseEntity<ErrorConfirmation> ecHandler(ErrorConfirmation e) {
        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
