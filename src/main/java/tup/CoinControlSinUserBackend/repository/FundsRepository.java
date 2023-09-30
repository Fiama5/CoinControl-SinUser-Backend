package tup.CoinControlSinUserBackend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tup.CoinControlSinUserBackend.model.Funds;
import tup.CoinControlSinUserBackend.model.PredefinedFund;
import tup.CoinControlSinUserBackend.model.User;

public interface FundsRepository extends JpaRepository<Funds, Long>{
    List<Funds> findByUserId(Long userId);
    Funds findByUserAndPredefinedFund(User user, PredefinedFund predefinedFund);
}
