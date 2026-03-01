package com.github.devlucasjava.apilucabank.dto.mapper;

import com.github.devlucasjava.apilucabank.dto.response.AuthResponse;
import com.github.devlucasjava.apilucabank.model.Users;

public class AuthMapper {

    public static AuthResponse toAuthResponse(Users users) {
        AuthResponse  response = new AuthResponse();
                response.setEmail(users.getEmail());
                response.setRole(users.getRole().toString());
                response.setTokenType("Beaver");
                return  response;
    }
}
