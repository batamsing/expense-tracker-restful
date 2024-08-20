package com.batamsingh.expensetracker.service;

import com.batamsingh.expensetracker.model.Expense;
import com.batamsingh.expensetracker.model.ExpenseGroup;
import com.batamsingh.expensetracker.model.Users;
import com.batamsingh.expensetracker.repository.ExpenseGroupRepository;
import com.batamsingh.expensetracker.repository.ExpenseRepository;
import com.batamsingh.expensetracker.utility.AuthenticatedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseGroupService {
    private final ExpenseGroupRepository expenseGroupRepository;

    private final ExpenseRepository expenseRepository;

    private final AuthenticatedUserService authenticatedUserService;

    @Autowired
    public ExpenseGroupService(ExpenseGroupRepository expenseGroupRepository, ExpenseRepository expenseRepository, AuthenticatedUserService authenticatedUserService) {
        this.expenseGroupRepository = expenseGroupRepository;
        this.expenseRepository = expenseRepository;
        this.authenticatedUserService = authenticatedUserService;
    }

    // creating expenseGroup
    public ExpenseGroup createGroup(ExpenseGroup group) {
        Users user = authenticatedUserService.getAuthenticatedUser();
        group.setUser(user);
        group.setCreatedOn(LocalDate.now());
        group.setUpdatedAt(LocalDateTime.now());
        return expenseGroupRepository.save(group);
    }

    // get expense group by id
    public ExpenseGroup findExpenseGroupById(Long id) {
        Users user = authenticatedUserService.getAuthenticatedUser();

        // verifying user and expense group
        return expenseGroupRepository.findByIdAndUser(id, user).orElseThrow(() -> new RuntimeException("ExpenseGroup not found with id: " + id));
    }

    // update expenseGroup
    public ExpenseGroup updateExpenseGroup(long id, ExpenseGroup expenseGroup) {
        // fetch expenseGroup by ID and user
        Users user = authenticatedUserService.getAuthenticatedUser();
        ExpenseGroup existingExpenseGroup = expenseGroupRepository.findByIdAndUser(id, user).orElseThrow(() -> new RuntimeException("ExpenseGroup not found with id: " + id));

        // update the existing expense group with new updated expense group

        existingExpenseGroup.setGroupName(expenseGroup.getGroupName());
        existingExpenseGroup.setDescription(expenseGroup.getDescription());
        existingExpenseGroup.setUpdatedAt(LocalDateTime.now());

        // save the updated expense group to the repository
        return expenseGroupRepository.save(existingExpenseGroup);
    }

    // fetch all expenseGroup
    public List<ExpenseGroup> getAllExpenseGroup() {
        Users user = authenticatedUserService.getAuthenticatedUser();
        return expenseGroupRepository.findByUser(user);
    }

    // delete expense group
    public void deleteExpenseGroup(long id) {
        // verify expense group id and user
        Users user = authenticatedUserService.getAuthenticatedUser();
        ExpenseGroup expenseGroup = expenseGroupRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("ExpenseGroup not found with id: " + id));
        expenseGroupRepository.delete(expenseGroup);
    }

    // adding expense in an expenseGroup
    public Expense addExpense(long id, Expense expense) {
        // fetch the ExpenseGroup by ID and user
        Users user = authenticatedUserService.getAuthenticatedUser();
        ExpenseGroup expenseGroup = expenseGroupRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("ExpenseGroup not found with id: " + id));

        // set the association between the expense and the group and the user
        expense.setExpenseGroup(expenseGroup);
        expense.setUser(user);
        expenseGroup.setUser(user);

        // save the new expense
        expenseRepository.save(expense);
        expenseGroupRepository.save(expenseGroup);

        return expense;
    }

    // fetch single expense group by expense group id and expense id
    public Expense findExpenseById(Long groupId, Long expenseId) {
        // verify users and ensure group exists
        findExpenseGroupById(groupId);
        return expenseRepository.findByIdAndExpenseGroupId(expenseId, groupId)
                .orElseThrow(() -> new RuntimeException("Expense not found with id " + expenseId + " in group " + groupId));
    }

    // update expense
    public Expense updateExpense(Long groupId, Long expenseId, Expense expense) {
        // Ensure the ExpenseGroup exists
        ExpenseGroup expenseGroup = findExpenseGroupById(groupId);

        // Ensure the Expense exists within the group
        Expense existingExpense = findExpenseById(groupId, expenseId);

        // Update the existing expense with the new details
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setDescription(expense.getDescription());
        existingExpense.setCategory(expense.getCategory());
        existingExpense.setUpdatedAt(LocalDateTime.now());

        // Update the 'updatedAt' field of the ExpenseGroup
        expenseGroup.setUpdatedAt(LocalDateTime.now());

        // Save the updated Expense
        Expense updatedExpense = expenseRepository.save(existingExpense);

        // Save the updated ExpenseGroup
        expenseGroupRepository.save(expenseGroup);

        // Return the updated Expense
        return updatedExpense;
    }

    // fetch expense list for a single group
    public List<Expense> getAllExpenseForGroup(long id) {
        Users user = authenticatedUserService.getAuthenticatedUser();

        // verifying user and expense group id
        ExpenseGroup expenseGroup = expenseGroupRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new RuntimeException("ExpenseGroup not found with id: " + id));

        // get list of expense
        return expenseRepository.findByExpenseGroup(expenseGroup);
    }

    // delete expense
    public void deleteExpense(Long groupId, Long expenseId) {
        Expense expense = findExpenseById(groupId,expenseId);
        expenseRepository.delete(expense);
    }
}
