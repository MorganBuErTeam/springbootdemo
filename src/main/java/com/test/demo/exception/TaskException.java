package com.test.demo.exception;

/**
 * 任务表自定义异常
 */
public class TaskException extends RuntimeException{

    public TaskException(){

    }

    public TaskException(String message){
        super(message);
    }

    public TaskException(Throwable cause){
        super(cause);
    }

    public TaskException(String message,Throwable cause){
        super(message,cause);
    }

    public TaskException(String message,Throwable cause,boolean enableSuppression,boolean writableStackTrace){
        super(message,cause,enableSuppression,writableStackTrace);
    }



}
