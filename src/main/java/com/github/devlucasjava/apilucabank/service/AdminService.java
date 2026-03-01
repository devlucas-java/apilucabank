package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.mapper.UsersMapper;
import com.github.devlucasjava.apilucabank.dto.request.UsersFilterRequest;
import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import com.github.devlucasjava.apilucabank.service.queryfilter.UserSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UsersRepository usersRepository;
    private final UsersMapper usersMapper;

    public Page<UsersResponse> findUsers(UsersFilterRequest filter, Integer size, Integer page) {
        if (size == null || size <= 0){size = 10;}
        if (page == null || page <= 1){page = 1;}
        Pageable pageable = PageRequest.of(page, size);

        Specification<Users> spec = Specification
                .where(UserSpec.firstNameContains(filter.firstName()))
                .or(UserSpec.lastNameContains(filter.lastName()))
                .and(UserSpec.isActive(filter.isActive()))
                .and(UserSpec.isLocked(filter.isLocked()));

        Page<Users> users =  usersRepository.findAll(spec, pageable);
        return users.map(usersMapper::toUsersResponse);
    }
}
