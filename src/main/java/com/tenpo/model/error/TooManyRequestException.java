package com.tenpo.model.error;

public class TooManyRequestException extends RuntimeException{

    public TooManyRequestException(String message){
        super(message);
    }
}
