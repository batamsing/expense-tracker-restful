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
    @PostMapping("/groups")
    public ResponseEntity<ExpenseGroup> createGroup(@RequestBody ExpenseGroup group) {
        try {
            ExpenseGroup expenseGroup = expenseGroupService.createGroup(group);
            return ResponseEntity.status(HttpStatus.CREATED).body(expenseGroup);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // get expenseGroupList
    @GetMapping("/groups")
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

    // create expense for a group
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

}
