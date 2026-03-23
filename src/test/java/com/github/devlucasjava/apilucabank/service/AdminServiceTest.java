package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.mapper.UserMapper;
import com.github.devlucasjava.apilucabank.dto.request.UsersFilterRequest;
import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private UserMapper userMapper;

    private Users user;
    private UsersResponse usersResponse;

    @BeforeEach
    void setup() {
        user = new Users();
        user.setFirstName("Lucas");
        user.setActive(true);
        user.setLocked(false);

        usersResponse = new UsersResponse();
        usersResponse.setFirstName("Lucas");
    }

    @Test
    void shouldReturnPagedUsersSuccessfully() {
        UsersFilterRequest filter = new UsersFilterRequest("Lucas", null, null, null, null, true, false, null, null);

        List<Users> usersList = List.of(user);
        Page<Users> usersPage = new PageImpl<>(usersList);

        when(usersRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(usersPage);

        when(userMapper.toUsersResponse(user))
                .thenReturn(usersResponse);

        Page<UsersResponse> result = adminService.findUsers(filter, 10, 1);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Lucas", result.getContent().get(0).getFirstName());

        verify(usersRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(userMapper).toUsersResponse(user);
    }
}
