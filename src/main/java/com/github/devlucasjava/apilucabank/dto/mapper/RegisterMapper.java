package com.github.devlucasjava.apilucabank.dto.mapper;

import com.github.devlucasjava.apilucabank.dto.request.RegisterRequest;
import com.github.devlucasjava.apilucabank.model.Users;

public class RegisterMapper {

    public static Users toUsers(RegisterRequest request) {
        Users users = new Users();
        users.setFirstName(request.getFirstName());
        users.setLastName(request.getLastName());
        users.setPassport(request.getPassport());
        users.setBirthDate(request.getBirthDate());
        users.setEmail(request.getEmail());
        users.setPassword(request.getPassword());
        return users;
    }
}
