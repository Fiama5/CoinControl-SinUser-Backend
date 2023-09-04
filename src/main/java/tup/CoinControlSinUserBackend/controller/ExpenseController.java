package tup.CoinControlSinUserBackend.controller;

import java.util.List;

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

import tup.CoinControlSinUserBackend.model.Category;
import tup.CoinControlSinUserBackend.model.Expense;
import tup.CoinControlSinUserBackend.repository.CategoryRepository;
import tup.CoinControlSinUserBackend.service.ExpenseService;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class ExpenseController {
    private final ExpenseService expenseService;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ExpenseController(ExpenseService expenseService, CategoryRepository categoryRepository) {
        this.expenseService = expenseService;
        this.categoryRepository = categoryRepository;
    }


    //Mostrar todos los gastos
    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getAllExpense() {
        List<Expense> expense = expenseService.findAllExpenses();
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }


    //Mostrar gasto por id
    @GetMapping("/find/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable("id") Long id) {
        Expense expense = expenseService.findExpenseById(id);
        return new ResponseEntity<>(expense, HttpStatus.OK);
    }

    //Actualizar gasto
    @PutMapping("/update")
    public ResponseEntity<Expense> updateExpense(@RequestBody Expense expense) {
        Expense updatedExpense = expenseService.updateExpense(expense);
        return new ResponseEntity<>(updatedExpense, HttpStatus.OK);
    }

    //Eliminar por id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteExpense(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Agregar Gasto por categoria
    @PostMapping("/add")
    public ResponseEntity<Expense> addExpensetoCategory(
            @RequestBody Expense expense,
            @RequestParam Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        expense.setCategory(category);
        Expense newExpense = expenseService.addExpense(expense);

        return new ResponseEntity<>(newExpense, HttpStatus.CREATED);
    }

/*Trae los gastos con la misma category */
@GetMapping("/find/bycategory/{categoryId}")
public List<Expense> getExepenseByCategory(
    @PathVariable Long categoryId){
    return expenseService.findExpenseByCategoryId(categoryId);
}

}
