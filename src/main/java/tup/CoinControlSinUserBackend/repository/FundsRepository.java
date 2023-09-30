package tup.CoinControlSinUserBackend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tup.CoinControlSinUserBackend.model.Funds;

public interface FundsRepository extends JpaRepository<Funds, Long>{
    List<Funds> findByUserId(Long userId);
}
