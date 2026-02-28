package com.github.devlucasjava.apilucabank.dto.mapper;

import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "role", source = "role.name")
    UsersResponse toResponse(Users user);
}