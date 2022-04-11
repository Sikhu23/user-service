package com.userservice.Model;

import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ApiError {

    private String code;

    private String message;

}

