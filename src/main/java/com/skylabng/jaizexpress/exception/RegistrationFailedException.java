package com.skylabng.jaizexpress.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus( HttpStatus.INTERNAL_SERVER_ERROR )
public class RegistrationFailedException extends Exception{

    @Serial
    private static final long serialVersionUID = -4961899491780449361L;

    public RegistrationFailedException( String message ){
        super( message );
    }

}
