package com.alamin.employeeManagementSystem.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserRepository, Long> {
    Optional<AppUserRepository> findByEmail (String email);
}
