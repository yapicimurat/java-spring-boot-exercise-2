package com.jwt.security.request;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;

    private String password;

}
