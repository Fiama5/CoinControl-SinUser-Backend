package tup.CoinControlSinUserBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tup.CoinControlSinUserBackend.model.Funds;

public interface FundsRepository extends JpaRepository<Funds, Long>{
    
}
