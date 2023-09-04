package tup.CoinControlSinUserBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tup.CoinControlSinUserBackend.model.Expense;
import tup.CoinControlSinUserBackend.repository.ExpenseRepository;
import tup.CoinControlSinUserBackend.service.NotFoundException.ExpenseNotFoundException;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Agregar nuevo gasto
    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Mostrar todos los gastos
    public List<Expense> findAllExpenses() {
        return expenseRepository.findAll();
    }

    // Actualizar un gasto
    public Expense updateExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Buscar un gasto por id
    public Expense findExpenseById(Long id) {
        return expenseRepository.findExpenseById(id)
                .orElseThrow(() -> new ExpenseNotFoundException("Expense by id" + id + "was not found"));
    }

    // Borrar un gasto por id
    @Transactional
    public void deleteExpense(long id) {
        expenseRepository.deleteExpenseById(id);
    }

    public List<Expense> findExpenseByCategoryId(Long categoryId){
        return expenseRepository.findByCategory_Id(categoryId);
    }

}
