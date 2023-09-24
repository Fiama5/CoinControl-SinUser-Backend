package tup.CoinControlSinUserBackend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
import tup.CoinControlSinUserBackend.model.Expense;
import tup.CoinControlSinUserBackend.model.Funds;
import tup.CoinControlSinUserBackend.repository.ExpenseRepository;
import tup.CoinControlSinUserBackend.repository.FundsRepository;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final FundsRepository fundsRepository;

  
@Autowired
    public ExpenseController(ExpenseRepository expenseRepository, FundsRepository fundsRepository) {
        this.expenseRepository = expenseRepository;
        this.fundsRepository = fundsRepository;
    }

    // Endpoint para obtener todos los gastos
    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Endpoint para obtener un gasto por ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()) {
            return new ResponseEntity<>(expense.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para actualizar un gasto por ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        Optional<Expense> existingExpense = expenseRepository.findById(id);
        if (existingExpense.isPresent()) {
            updatedExpense.setId(id); // Asegúrate de que el ID coincida
            Expense savedExpense = expenseRepository.save(updatedExpense);
            return new ResponseEntity<>(savedExpense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para eliminar un gasto por ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (expense.isPresent()) {
            expenseRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense, @RequestParam("fundsId") Long fundsId) {
        // Obtener el fondo seleccionado
        Funds funds = fundsRepository.findById(fundsId)
                .orElseThrow(() -> new EntityNotFoundException("Funds not found with id: " + fundsId));
    
        // Asociar el gasto con el fondo
        expense.setFunds(funds);
    
        // Restar el monto del gasto de los fondos disponibles
        double newFundsAmount = funds.getAmount() - expense.getAmount();
        funds.setAmount(newFundsAmount);
    
        // Guardar el gasto y actualizar los fondos disponibles
        Expense savedExpense = expenseRepository.save(expense);
        fundsRepository.save(funds);
    
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }
    

    // Endpoint para obtener los gastos de un usuario y una categoría específicos
    @GetMapping("/find/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<Expense>> getExpensesByUserAndCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId) {
        List<Expense> expenses = expenseRepository.findByUserIdAndCategoryId(userId, categoryId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

}
