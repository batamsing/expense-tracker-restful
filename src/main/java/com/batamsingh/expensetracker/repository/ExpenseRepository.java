package com.batamsingh.expensetracker.repository;

import com.batamsingh.expensetracker.model.Expense;
import com.batamsingh.expensetracker.model.ExpenseGroup;
import com.batamsingh.expensetracker.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(Users user);
    List<Expense> findByExpenseGroup(ExpenseGroup expenseGroup);
    Optional<Expense> findByIdAndUser(Long id, Users user);
    Optional<Expense> findByIdAndExpenseGroupId(Long id, Long groupId);
}
