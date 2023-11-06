package tup.CoinControlSinUserBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tup.CoinControlSinUserBackend.model.Expense;
import tup.CoinControlSinUserBackend.repository.ExpenseRepository;

import java.time.LocalDate;
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

    // Metodo para obtener una lista de gastos del usuario y de una categoria en
    // concreto de los ultimos 15 dias
    // Toma dos parametros, userId y categoriaId, userId se utilizara para saber que
    // usuario esta logueado y solo traer los gastos de ese usuario
    // CategoriaId se utiliza solo para traer los gastos de una categoria y que sea
    // mas legible
    // Se espera que devuelva una lista de gastos (List<Expense>)
    public List<Expense> getExpensesLast15Days(Long userId, Long categoryId) {
        // Se crea una variable de tipo "Local date", que representa la fecha actual y a
        // esa variable se le restan 15 dias. Esto lo que permite es establece el limite
        // inferior del rango de fechas para buscar los gastos asociados a este rango
        LocalDate startDate = LocalDate.now().minusDays(15);
        // Se crea una variable "endate" de tipo LocalDate, que representa la fecha
        // actual y esto establece el limite superior del rango de fechas
        LocalDate endDate = LocalDate.now();

        //Llama al metodo findExpensesDateRange del repositorio y le pasa por parametros "userId", "categoryId", "startDate" y "endDate" y retorna la respuesta del repositorio
        return expenseRepository.findExpensesDateRange(userId, categoryId, startDate, endDate);
    }

    //Mismo metodo que getExpensesLast15Days, solo se cambia el nombre del metodo y los dias de la variable startDate
    public List<Expense> getExpensesLast30Days(Long userId, Long categoryId) {
        LocalDate startDate = LocalDate.now().minusDays(30);
        LocalDate endDate = LocalDate.now();

        return expenseRepository.findExpensesDateRange(userId, categoryId, startDate, endDate);
    }

}
