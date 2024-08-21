package com.batamsingh.expensetracker.controller;

import com.batamsingh.expensetracker.model.Expense;
import com.batamsingh.expensetracker.model.ExpenseGroup;
import com.batamsingh.expensetracker.service.ExpenseGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense-groups")
public class ExpenseGroupController {

    private final ExpenseGroupService expenseGroupService;

    @Autowired
    public ExpenseGroupController(ExpenseGroupService expenseGroupService) {
        this.expenseGroupService = expenseGroupService;
    }

    // create new expense group
    @PostMapping
    public ResponseEntity<ExpenseGroup> createGroup(@RequestBody ExpenseGroup group) {
        try {
            ExpenseGroup expenseGroup = expenseGroupService.createGroup(group);
            return ResponseEntity.status(HttpStatus.CREATED).body(expenseGroup);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // get expenseGroupList
    @GetMapping
    public ResponseEntity<List<ExpenseGroup>> getExpenseGroupList() {
        try {
            List<ExpenseGroup> groups = expenseGroupService.getAllExpenseGroup();
            return ResponseEntity.ok(groups);
        } catch (Exception e) {
            // Handle exception and return an appropriate response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // get a specific expenseGroup by id
    @GetMapping("/{groupId}")
    public ResponseEntity<ExpenseGroup> getExpenseGroupById(@PathVariable Long groupId) {
        try {
            ExpenseGroup expenseGroup = expenseGroupService.findExpenseGroupById(groupId);
            return ResponseEntity.ok(expenseGroup);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Update an ExpenseGroup
    @PutMapping("/{groupId}")
    public ResponseEntity<ExpenseGroup> updateExpenseGroup(@PathVariable Long groupId, @RequestBody ExpenseGroup expenseGroup) {
        ExpenseGroup updatedExpenseGroup = expenseGroupService.updateExpenseGroup(groupId, expenseGroup);
        return new ResponseEntity<>(updatedExpenseGroup, HttpStatus.OK);
    }

    // Delete an ExpenseGroup
    @DeleteMapping("/{groupId}")
    public ResponseEntity<Void> deleteExpenseGroup(@PathVariable Long groupId) {
        expenseGroupService.deleteExpenseGroup(groupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // create a new expense in a specific ExpenseGroup
    @PostMapping("/{groupId}/expenses")
    public ResponseEntity<Expense> createExpense(@PathVariable Long groupId, @RequestBody Expense expense) {
        try {
            Expense createExpense = expenseGroupService.addExpense(groupId, expense);
            return ResponseEntity.status(HttpStatus.CREATED).body(createExpense);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // fetch all expenses for a specific group
    @GetMapping("/{groupId}/expenses")
    public ResponseEntity<List<Expense>> getAllExpenseForGroup(@PathVariable Long groupId) {
        try {
            List<Expense> expenses = expenseGroupService.getAllExpenseForGroup(groupId);
            return ResponseEntity.status(HttpStatus.OK).body(expenses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // fetch a specific Expense by ID within a group
    @GetMapping("/{groupId}/expenses/{expenseId}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long groupId, @PathVariable Long expenseId) {
        Expense expense = expenseGroupService.findExpenseById(groupId, expenseId);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    // update an expense within a specific ExpenseGroup
    @PutMapping("/{groupId}/expenses/{expenseId}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long groupId, @PathVariable Long expenseId, @RequestBody Expense expense) {
        Expense updatedExpense = expenseGroupService.updateExpense(groupId, expenseId, expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    // Delete an Expense from a specific ExpenseGroup
    @DeleteMapping("/{groupId}/expenses/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long groupId, @PathVariable Long expenseId) {
        expenseGroupService.deleteExpense(groupId, expenseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
