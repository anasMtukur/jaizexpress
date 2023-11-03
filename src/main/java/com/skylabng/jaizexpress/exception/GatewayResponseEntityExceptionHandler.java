package com.skylabng.jaizexpress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GatewayResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex ) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler(value = { RuntimeException.class, Exception.class })
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex ) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
    }

    @ExceptionHandler(value = UserAlreadyExistException.class )
    protected ResponseEntity<Object> handleUserAlreadyExist( UserAlreadyExistException ex ) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.FORBIDDEN );
    }

    @ExceptionHandler(value = RegistrationFailedException.class )
    protected ResponseEntity<Object> handleInvalidDriverStatus( RegistrationFailedException ex ) {
        return new ResponseEntity<>( ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR );
    }

}
