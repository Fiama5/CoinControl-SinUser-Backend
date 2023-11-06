package tup.CoinControlSinUserBackend.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tup.CoinControlSinUserBackend.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);

    // Consulta para sumar el total de los gastos por categoria y por el usuario
    // logueado
    // @Query se utiliza en JPA para definir una consulta personalizada en lenguaje
    // JPQL (Java Persistence Query Language) o SQL nativo.
    @Query("SELECT e.category.name, SUM(e.amount) FROM Expense e WHERE e.user.id = :userID GROUP BY e.category.name")
    List<Object[]> sumOfExpensesByCategoryForUser(@Param("userID") Long userID);



    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND e.category.id = :categoryId AND e.date >= :startDate AND e.date <= :endDate ORDER BY ABS(DATEDIFF(CURRENT_DATE, e.date))")
    // La consulta busca gastos que cumplan con el userid asociado al gasto y la
    // categoria asociada al gasto
    // e.date >= :startDate AND e.date <= :endDate indica que la fecha del gasto
    // debe estar dentro de un rango de fechas definido por startDate y endDate.
    // os resultados se ordenan por la diferencia absoluta en días entre la fecha
    // actual (CURRENT_DATE) y la fecha del gasto (e.date).
    List<Expense> findExpensesDateRange(
            @Param("userId") Long userId,  //Parametro para obtener el id del usuario y pasarlo a la consukta
            @Param("categoryId") Long categoryId, //Parametro para obtener la categoryid y pasarlo a la consulta
            @Param("startDate") LocalDate startDate, //Parametro para obtener la fecha actual y poder pasarlo a la consulta
            @Param("endDate") LocalDate endDate);   //Parametro que define la fecha final para pasarlo por consulta

}
