package com.github.devlucasjava.apilucabank.repository;

import com.github.devlucasjava.apilucabank.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String>{
}
