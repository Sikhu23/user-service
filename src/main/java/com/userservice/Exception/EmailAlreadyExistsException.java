package com.userservice.Exception;

public class EmailAlreadyExistsException extends RuntimeException{

        public EmailAlreadyExistsException(String message){
            super(message);
        }

}
