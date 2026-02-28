package com.github.devlucasjava.apilucabank.repository;

import com.github.devlucasjava.apilucabank.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {

    @Query("""
        SELECT u 
        FROM Users u 
        WHERE u.email = :login 
        OR u.passaport = :login
        """)
    Optional<Users> findByEmailOrPasaport(@Param("login") String login);

    Optional<Users> findByEmail(String email);
    Optional<Users> findByPassaport(String passaport);

}
