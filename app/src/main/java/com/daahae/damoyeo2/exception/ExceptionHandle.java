package com.daahae.damoyeo2.exception;

public class ExceptionHandle extends Exception {

    public ExceptionHandle(String msg){
        super(msg);
    }

    public ExceptionHandle(Exception ex){
        super(ex);
    }
}
