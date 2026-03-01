package com.github.devlucasjava.apilucabank.security;


import com.github.devlucasjava.apilucabank.exception.CustomAuthException;
import com.github.devlucasjava.apilucabank.model.Users;
import com.github.devlucasjava.apilucabank.repository.UsersRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        private UsersRepository usersRepository;

        public CustomUserDetailsService(UsersRepository usersRepository){
                this.usersRepository = usersRepository;
        };

        @Override
        public Users loadUserByUsername(String login) throws UsernameNotFoundException {
                return usersRepository.findByEmailOrPassport(login).orElseThrow(() -> new CustomAuthException("User not found with email or passort: " + login));
        }
}
