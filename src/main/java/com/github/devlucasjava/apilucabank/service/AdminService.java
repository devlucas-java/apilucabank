package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.mapper.UserMapper;
import com.github.devlucasjava.apilucabank.dto.request.UsersFilterRequest;
import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.exception.ResourceNotFoundException;
import com.github.devlucasjava.apilucabank.model.Authority;
import com.github.devlucasjava.apilucabank.model.Role;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.RoleRepository;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import com.github.devlucasjava.apilucabank.service.queryfilter.UserSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final int DEFAULT_PAGE_NUMBER = 0;

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public Page<UsersResponse> findUsers(UsersFilterRequest filter, Integer size, Integer page) {
        int pageSize = (size == null || size <= 0) ? DEFAULT_PAGE_SIZE : size;
        int pageNumber = (page == null || page < 1) ? DEFAULT_PAGE_NUMBER : page - 1;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        log.debug("Searching users | page: {}, size: {}", pageNumber + 1, pageSize);

        Specification<Users> spec = buildSpecification(filter);
        Page<Users> usersPage = usersRepository.findAll(spec, pageable);

        log.debug("Total users found: {}", usersPage.getTotalElements());
        return usersPage.map(userMapper::toUsersResponse);
    }

    private Specification<Users> buildSpecification(UsersFilterRequest filter) {
        Specification<Users> spec = Specification
                .where(UserSpec.firstNameContains(filter.firstName()))
                .or(UserSpec.lastNameContains(filter.lastName()))
                .and(UserSpec.isActive(filter.isActive()))
                .and(UserSpec.isLocked(filter.isLocked()));

        log.debug("User search specification created | firstName: {}, lastName: {}, isActive: {}, isLocked: {}",
                filter.firstName(), filter.lastName(), filter.isActive(), filter.isLocked());

        return spec;
    }


    public UsersResponse changeUserRole(UUID id, String roleName) {

        if (!roleName.equals(roleName.toUpperCase())) {
            throw new IllegalArgumentException("Role must be uppercase");
        }

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.setRole(role);

        usersRepository.save(user);

        return userMapper.toUsersResponse(user);
    }
}