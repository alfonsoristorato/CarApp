package com.alfonso.CarApp.exception;

public class AttributeMissingException extends RuntimeException{
    public AttributeMissingException(){
        super("Attribute Missing");
    }
}
