package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.exception.ResourceNotFoundException;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    public UsersResponse getUserAuthenticated(Users users) {
        Users user = usersRepository.findByEmailOrPasaport(users.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UsersResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .isLocked(user.isLocked())
                .passport(user.getPassaport())
                .role(user.getRole().toString())
                .updatedAt(user.getUpdatedAt())
                .createdAt(user.getCreatedAt())
                .isActive(user.isActive())
                .id(user.getId().toString())
                .build();
    }
}
