package com.github.devlucasjava.apilucabank.service.queryfilter;

import com.github.devlucasjava.apilucabank.model.Users;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpec {

    public static Specification<Users> firstNameContains(String firstName) {
        return (root, query, cb) -> {
            if (firstName == null) return null;
            return cb.like(
                    cb.lower(root.get("firstName")),
                    "%" + firstName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Users> lastNameContains(String lastName) {
        return (root, query, cb) -> {
            if (lastName == null) return null;
            return cb.like(
                    cb.lower(root.get("lastName")),
                    "%" + lastName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Users> isActive(Boolean isActive) {
        return (root, query, cb) -> {
            if (isActive == null) return null;
            return cb.equal(root.get("isActive"), isActive);
        };
    }

    public static Specification<Users> isLocked(Boolean isLocked) {
        return (root, query, cb) -> {
            if (isLocked == null) return null;
            return cb.equal(root.get("isLocked"), isLocked);
        };
    }

    public static Specification<Users> createdBetween(LocalDate start, LocalDate end) {
        return (root, query, cb) -> {
            if (start == null || end == null) return null;
            return cb.between(root.get("createdAt"), start, end);
        };
    }
    }