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
import tup.CoinControlSinUserBackend.service.ExpenseService;

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class ExpenseController {

    private final ExpenseRepository expenseRepository;
    private final FundsRepository fundsRepository;
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseRepository expenseRepository, FundsRepository fundsRepository,
            ExpenseService expenseService) {
        this.expenseRepository = expenseRepository;
        this.fundsRepository = fundsRepository;
        this.expenseService = expenseService;
    }

    // Endpoint para obtener todos los gastos
    @GetMapping("/all")
    public ResponseEntity<List<Expense>> getAllExpenses() {
        // Obtiene todos los gastos del repositorio
        List<Expense> expenses = expenseRepository.findAll();

        // Retorna una respuesta con la lista de todos los gastos
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    // Endpoint para obtener un gasto por ID
    @GetMapping("/find/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        // Busca el gasto por el ID proporcionado
        Optional<Expense> expense = expenseRepository.findById(id);

        // Verifica si el gasto fue encontrado en el repositorio
        if (expense.isPresent()) {
            // Si el gasto se encuentra, retorna una respuesta con el gasto
            return new ResponseEntity<>(expense.get(), HttpStatus.OK);
        } else {
            // Si el gasto no se encuentra, retorna una respuesta con el código de estado
            // HTTP NOT FOUND (404)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para actualizar un gasto por ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @RequestBody Expense updatedExpense) {
        // Busca el gasto existente por el ID proporcionado
        Optional<Expense> existingExpense = expenseRepository.findById(id);

        // Verifica si el gasto existente fue encontrado en el repositorio
        if (existingExpense.isPresent()) {
            updatedExpense.setId(id); // Asegura que el ID del gasto actualizado coincida con el ID proporcionado
            Expense savedExpense = expenseRepository.save(updatedExpense); // Actualiza el gasto en la base de datos
            return new ResponseEntity<>(savedExpense, HttpStatus.OK); // Retorna el gasto actualizado
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Retorna el código de estado
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
    // Este metodo recibe un objeto Expense en el cuerpo de la solicitud
    // (RequestBody) y devuelve un ResponseEntity, que es una respuesta HTTP.
    public ResponseEntity<Expense> createExpense(@RequestBody Expense expense) {
        try {
            // Obtener el fondo relacionado con el gasto seleccionado.
            // Se busca en el repositorio de fondos utilizando el fundsid que esta asociado
            // al gasto
            Funds funds = fundsRepository.findById(expense.getFunds().getId())
                    // En todo caso de que no se logre encontrar el fondo con el gasto asociado
                    // Se lanza una excepion del tipo EntityNotFoundException
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Funds not found with id: " + expense.getFunds().getId()));

            // Asociar el gasto con el fondo recuperado de fundsRepository
            expense.setFunds(funds);

            // Restar el monto del gasto de los fondos disponibles
            double newFundsAmount = funds.getAmount() - expense.getAmount();
            // Se actualiza el monto de fondos disponibles en el objeto Funds con el nuevo
            // valor
            funds.setAmount(newFundsAmount);

            // Guardar el gasto y actualizar los fondos disponibles
            Expense savedExpense = expenseRepository.save(expense);
            fundsRepository.save(funds);
            // Devuelve el gasto creado y la respuesta http
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
        // El parámetro userID se puede acceder en la lógica del método para usarlo en
        // la obtención de gastos.

        // Lógica para obtener gastos por categoría para el userID proporcionado
        List<Object[]> expensesSumByCategory = expenseRepository.sumOfExpensesByCategoryForUser(userID);
        // Se asume que expenseRepository tiene un método sumOfExpensesByCategoryForUser
        // que toma userID como parámetro y devuelve la lista de gastos por categoría.

        // Mapear los resultados a un mapa de categoría a total gastado
        Map<String, Double> categoryExpenses = new HashMap<>();
        // Se recorren los resultados obtenidos (presumiblemente cada fila de datos
        // obtenida de la base de datos) y se asignan a un Map<String, Double>. Cada
        // categoría se convierte en la clave del mapa y el valor asociado es el total
        // de gastos para esa categoría.
        for (Object[] result : expensesSumByCategory) {
            String category = (String) result[0];
            Double sum = (Double) result[1];
            categoryExpenses.put(category, sum);
        }
        // Se devuelve un objeto ResponseEntity que contiene el Map de totales de gastos
        // por categoría para el usuario con el código de estado HTTP OK
        return new ResponseEntity<>(categoryExpenses, HttpStatus.OK);
    }

    // Metodos aun no integrados en angular

    // Controller para obtener los gastos de un usuario y una categoria especificos
    // de los ultimos 15 dias
    @GetMapping("/last15days")
    // Se crea un metodo que desea obtener una lista de gastos
    public List<Expense> getExpensesLast15Days(
            // Recibe dos parametros, un userIdd y un categoryId
            @RequestParam Long userId,
            @RequestParam Long categoryId) {
        // Retorna el metodo getExpensesLast15Days, pasandole por parametros el userId y
        // categoryId al servicio
        return expenseService.getExpensesLast15Days(userId, categoryId);
    }

    // Mismo controller que "/last15days", a diferencia de que usa otro metodo para
    // que sean 30 dias en vez de 15
    @GetMapping("/last30days")
    public List<Expense> getExpensesLast30Days(
            @RequestParam Long userId,
            @RequestParam Long categoryId) {
        return expenseService.getExpensesLast30Days(userId, categoryId);
    }
}
