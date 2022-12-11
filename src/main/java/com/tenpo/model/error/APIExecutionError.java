package com.tenpo.model.error;

public class APIExecutionError extends RuntimeException{

    public APIExecutionError(String message){
        super(message);
    }
}
