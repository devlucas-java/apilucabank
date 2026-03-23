package com.github.devlucasjava.apilucabank.controller;

import com.github.devlucasjava.apilucabank.dto.request.UsersFilterRequest;
import com.github.devlucasjava.apilucabank.dto.response.UsersResponse;
import com.github.devlucasjava.apilucabank.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Administrative operations for users")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('MANAGE_USER')")
    @Operation(summary = "Search users", description = "Search users using optional filter parameters. All filters are optional.")
    public ResponseEntity<Page<UsersResponse>> findUsers(
            @ParameterObject UsersFilterRequest filter,
            @RequestParam(required = false)
            @Parameter(description = "Page number (pagination)", example = "0") Integer page,
            @RequestParam(required = false)
            @Parameter(description = "Number of records per page", example = "10") Integer size
    ) {
        log.debug("Admin /users endpoint called | filter: firstName={}, lastName={}, isActive={}, isLocked={}, page={}, size={}",
                filter.firstName(), filter.lastName(), filter.isActive(), filter.isLocked(), page, size);

        Page<UsersResponse> users = adminService.findUsers(filter, size, page);

        log.debug("Admin /users endpoint returned {} users", users.getTotalElements());

        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    @PatchMapping("/users/{id}/role")
    @PreAuthorize("hasAuthority('MANAGE_USER')")
    @Operation(
            summary = "Change user role",
            description = "Promotes or demotes a user by changing their role. Allowed values: ADMIN, USER."
    )
    public ResponseEntity<UsersResponse> changeUserRole(
            @PathVariable
            @Parameter(description = "User ID", example = "UUID") UUID id,

            @RequestParam
            @Parameter(description = "Role name (must be uppercase)", example = "ADMIN")
            String role
    ) {
        log.info("PATCH /admin/users/{}/role | newRole={}", id, role);

        UsersResponse response = adminService.changeUserRole(id, role);

        log.info("User role updated | id={}, newRole={}", id, role);

        return ResponseEntity.ok(response);
    }
}