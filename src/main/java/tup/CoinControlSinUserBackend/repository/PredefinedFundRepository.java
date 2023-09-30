package tup.CoinControlSinUserBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tup.CoinControlSinUserBackend.model.PredefinedFund;

public interface PredefinedFundRepository extends JpaRepository<PredefinedFund, Long> {

}
