package com.tenpo.model.error;

public class PercentageAPIMaxTriesException extends RuntimeException{
    public PercentageAPIMaxTriesException(String message){
        super(message);
    }
}
