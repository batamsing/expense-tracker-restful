package com.batamsingh.expensetracker.repository;


import com.batamsingh.expensetracker.model.Expense;
import com.batamsingh.expensetracker.model.ExpenseGroup;
import com.batamsingh.expensetracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseGroupRepository extends JpaRepository<ExpenseGroup, Long> {
    List<ExpenseGroup> findByUser(Users user);
    Optional<ExpenseGroup> findByIdAndUser(Long id, Users user);
}
