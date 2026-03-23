package com.github.devlucasjava.apilucabank.service;

import com.github.devlucasjava.apilucabank.dto.response.AccountResponse;
import com.github.devlucasjava.apilucabank.exception.ResourceNotFoundException;
import com.github.devlucasjava.apilucabank.model.Account;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.AccountRepository;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UsersRepository usersRepository;

    private Users user;
    private Account account;

    @BeforeEach
    void setup() {
        user = new Users();
        user.setId(UUID.randomUUID());
        user.setFirstName("Lucas");

        account = new Account();
        account.setBalance(new BigDecimal(500));
        account.setId(UUID.randomUUID());

        user.setAccount(account);
        account.setUser(user);
    }

    @Test
    void shouldAccountReturnSuccessully(){

        when(accountRepository.findByUser(user))
                .thenReturn(Optional.of(account));

        AccountResponse accountFound = accountService.getMyAccount(user);

        assertNotNull(accountFound);
        assertNotNull(accountFound.getUserId());
        assertEquals(accountFound.getUserId(), user.getId());
        verify(accountRepository).findByUser(user);
    }

    @Test
    void shouldAccountReturnException() {
        when(accountRepository.findByUser(user))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                accountService.getMyAccount(user));

        verify(accountRepository).findByUser(user);
    }
}
