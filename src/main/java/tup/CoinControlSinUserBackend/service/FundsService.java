package tup.CoinControlSinUserBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tup.CoinControlSinUserBackend.repository.FundsRepository;

@Service
public class FundsService {
    private final FundsRepository fundsRepository;

    @Autowired
    public FundsService(FundsRepository fundsRepository) {
        this.fundsRepository = fundsRepository;
    }

}
