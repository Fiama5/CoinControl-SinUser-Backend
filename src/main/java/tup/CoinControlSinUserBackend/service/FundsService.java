package tup.CoinControlSinUserBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tup.CoinControlSinUserBackend.model.Funds;
import tup.CoinControlSinUserBackend.repository.FundsRepository;

@Service
public class FundsService {
    private final FundsRepository fundsRepository;

    @Autowired
    public FundsService(FundsRepository fundsRepository) {
        this.fundsRepository = fundsRepository;
    }

    //Mostrar todos los fondos
    public List<Funds> findAllFunds(){
        return fundsRepository.findAll();
    }

    public List<Funds> getFundsByUser (Long userId){
        return fundsRepository.findByUserId(userId);
    }

}
