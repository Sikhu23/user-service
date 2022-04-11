package com.userservice.Exception;

public class UserIdExistsException extends RuntimeException{
    public UserIdExistsException(String msg){
        super(msg);
    }
}
