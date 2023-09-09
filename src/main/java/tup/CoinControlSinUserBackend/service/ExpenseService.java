package tup.CoinControlSinUserBackend.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tup.CoinControlSinUserBackend.model.Expense;
import tup.CoinControlSinUserBackend.repository.ExpenseRepository;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }


    // Método para obtener gastos por usuario y categoría
    public List<Expense> getExpensesByUserAndCategory(Long userId, Long categoryId) {
        return expenseRepository.findByUserIdAndCategoryId(userId, categoryId);
    }
}

