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

    // Mostrar todos los fondos
    public List<Funds> findAllFunds() {
        return fundsRepository.findAll();
    }

    // Mostrar fondos por id de usuario
    // Devuelve una lista de fondos(Funds) y recibe por parametro un user id que va
    // a buscar los fondos de ese user id
    public List<Funds> getFundsByUser(Long userId) {
        // Se llama al metodo findByUserId que busca en la fuente de datos todos los
        // fondos relacionados con el userId proporcionado.
        return fundsRepository.findByUserId(userId);
    }

}
