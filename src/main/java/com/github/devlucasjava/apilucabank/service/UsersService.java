package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.mapper.UsersMapper;
import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.exception.ResourceNotFoundException;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    @Autowired
    private UsersMapper usersMapper;
    private final UsersRepository usersRepository;

    public UsersResponse getUserAuthenticated(Users users) {
        Users user = usersRepository.findByEmailOrPassport(users.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return usersMapper.toUsersResponse(user);
    }
}
