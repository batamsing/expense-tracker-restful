package com.batamsingh.expensetracker.service;

import com.batamsingh.expensetracker.model.Expense;
import com.batamsingh.expensetracker.model.Users;
import com.batamsingh.expensetracker.repository.ExpenseRepository;
import com.batamsingh.expensetracker.utility.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository, AuthenticatedUserService authenticatedUserService) {
        this.expenseRepository = expenseRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    public Expense createExpense(Expense expense) {
        Users user = authenticatedUserService.getAuthenticatedUser();
        expense.setUser(user);
        expense.setTransactionDate(LocalDate.now());
        expense.setUpdatedAt(LocalDateTime.now());
        return expenseRepository.save(expense);
    }

    public Expense getExpenseById(Long id) {
        Users user = authenticatedUserService.getAuthenticatedUser();
        return expenseRepository.findByIdAndUser(id, user).orElse(null);
    }

    public List<Expense> getAllExpenses() {
        Users user = authenticatedUserService.getAuthenticatedUser();
        return expenseRepository.findByUser(user);
    }

    public Expense updateExpense(Expense updatedExpense) {
        Users user = authenticatedUserService.getAuthenticatedUser();
        Expense existingExpense = expenseRepository.findByIdAndUser(updatedExpense.getId(), user).orElse(null);

        if (existingExpense != null) {
            existingExpense.setAmount(updatedExpense.getAmount());
            existingExpense.setCategory(updatedExpense.getCategory());
            existingExpense.setDescription(updatedExpense.getDescription());
            existingExpense.setTransactionDate(updatedExpense.getTransactionDate());
            existingExpense.setUpdatedAt(updatedExpense.getUpdatedAt());

            return expenseRepository.save(existingExpense);
        } else {
            return null; // expense not found or user not authorized
        }
    }
}
