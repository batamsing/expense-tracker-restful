package com.batamsingh.expensetracker.repository;

import com.batamsingh.expensetracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyUserRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
    Users findByEmail(String email);
}
