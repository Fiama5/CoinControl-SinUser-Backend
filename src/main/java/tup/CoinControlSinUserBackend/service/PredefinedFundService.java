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

    public List<PredefinedFund> findAllPredefinedFund(){
        return predefinedFundRepository.findAll();
    }
    
}
