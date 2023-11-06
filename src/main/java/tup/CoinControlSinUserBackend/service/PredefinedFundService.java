package tup.CoinControlSinUserBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tup.CoinControlSinUserBackend.model.PredefinedFund;
import tup.CoinControlSinUserBackend.repository.PredefinedFundRepository;

@Service
public class PredefinedFundService {
    private final PredefinedFundRepository predefinedFundRepository;

    @Autowired
    public PredefinedFundService(PredefinedFundRepository predefinedFundRepository) {
        this.predefinedFundRepository = predefinedFundRepository;
    }

    //Funcion que retorna todos los fondos predefinidos
    //Crea una lista de fondos predefinidos y utiliza el metodo findall para traer a todos
    public List<PredefinedFund> findAllPredefinedFund(){
        return predefinedFundRepository.findAll();
    }
    
}
