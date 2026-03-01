package com.github.devlucasjava.apilucabank.repository;

import com.github.devlucasjava.apilucabank.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsersRepository extends JpaRepository<Users, UUID>, JpaSpecificationExecutor<Users> {

    @Query("""
        SELECT u 
        FROM Users u 
        WHERE u.email = :login 
        OR u.passport = :login
        """)
    Optional<Users> findByEmailOrPassport(@Param("login") String login);

    Optional<Users> findByEmail(String email);
    Optional<Users> findByPassport(String passaport);
}
