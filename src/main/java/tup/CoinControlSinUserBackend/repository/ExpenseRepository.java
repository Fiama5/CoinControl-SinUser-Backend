package tup.CoinControlSinUserBackend.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;

import tup.CoinControlSinUserBackend.model.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{


    List<Expense> findByUserIdAndCategoryId(Long userId, Long categoryId);
    
}
