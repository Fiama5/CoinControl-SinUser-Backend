package tup.CoinControlSinUserBackend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    // Endpoint para eliminar un gasto por ID y actualiza los fondos devolviendo el
    // dinero gastado
    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Funds> deleteExpense(@PathVariable Long expenseId) {
        // Obtén el gasto que se va a eliminar
        Expense expense = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + expenseId));

        // Obtén el ID del fondo asociado al gasto
        Long fundsId = expense.getFunds().getId();

        // Elimina el gasto de la base de datos
        expenseRepository.delete(expense);

        // Actualiza los fondos relacionados con el ID del fondo obtenido
        Funds funds = fundsRepository.findById(fundsId)
                // Manejo de errores
                .orElseThrow(() -> new EntityNotFoundException("Funds not found with id: " + fundsId));

        // Restaura el monto de los fondos eliminados por este gasto
        double newFundsAmount = funds.getAmount() + expense.getAmount();
        // Se actualiza el monto de fondos disponibles en el objeto Funds con el nuevo
        // valor
        funds.setAmount(newFundsAmount);
        // Actualiza el fondo en la base de datos
        fundsRepository.save(funds);

        // Devuelve los fondos actualizados después de eliminar el gasto
        return ResponseEntity.ok(funds);
    }

    // Agregar un gasto nuevo
    @PostMapping("/add")
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        try {
            // Obtener el fondo seleccionado
            Funds funds = fundsRepository.findById(expense.getFunds().getId())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Funds not found with id: " + expense.getFunds().getId()));

            // Asociar el gasto con el fondo
            expense.setFunds(funds);

            // Restar el monto del gasto de los fondos disponibles
            double newFundsAmount = funds.getAmount() - expense.getAmount();
            // Se actualiza el monto de fondos disponibles en el objeto Funds con el nuevo
            // valor
            funds.setAmount(newFundsAmount);

            // Guardar el gasto y actualizar los fondos disponibles
            Expense savedExpense = expenseRepository.save(expense);
            fundsRepository.save(funds);

            return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
        } catch (Exception e) {
            // Manejo de excepciones
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para obtener los gastos de un usuario y una categoría específicos
    @GetMapping("/find/user/{userId}/category/{categoryId}")
    public ResponseEntity<List<Expense>> getExpensesByUserAndCategory(
            @PathVariable Long userId,
            @PathVariable Long categoryId) {
        List<Expense> expenses = expenseRepository.findByUserIdAndCategoryId(userId, categoryId);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

@GetMapping("/sum-by-category")
public ResponseEntity<Map<String, Double>> getSumOfExpensesByCategory(@RequestParam("userID") Long userID) {
    // El parámetro userID se puede acceder en la lógica del método para usarlo en la obtención de gastos.

    // Lógica para obtener gastos por categoría para el userID proporcionado
    List<Object[]> expensesSumByCategory = expenseRepository.sumOfExpensesByCategoryForUser(userID);
    // Se asume que expenseRepository tiene un método sumOfExpensesByCategoryForUser que toma userID como parámetro y devuelve la lista de gastos por categoría.

    // Mapear los resultados a un mapa de categoría a total gastado
    Map<String, Double> categoryExpenses = new HashMap<>();
    for (Object[] result : expensesSumByCategory) {
        String category = (String) result[0];
        Double sum = (Double) result[1];
        categoryExpenses.put(category, sum);
    }

    return new ResponseEntity<>(categoryExpenses, HttpStatus.OK);
}


}
