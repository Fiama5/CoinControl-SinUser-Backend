package tup.CoinControlSinUserBackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tup.CoinControlSinUserBackend.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);

    @Query("SELECT e.category.name, SUM(e.amount) FROM Expense e WHERE e.user.id = :userID GROUP BY e.category.name")
    List<Object[]> sumOfExpensesByCategoryForUser(@Param("userID") Long userID);
}

