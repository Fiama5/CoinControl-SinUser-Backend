package tup.CoinControlSinUserBackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tup.CoinControlSinUserBackend.model.Funds;
import tup.CoinControlSinUserBackend.repository.FundsRepository;

@RestController
@RequestMapping("/funds")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})
public class FundsController {

    private final FundsRepository fundsRepository;
    
@Autowired
    public FundsController(FundsRepository fundsRepository) {
        this.fundsRepository = fundsRepository;
    }


    @PostMapping("/add")
    public ResponseEntity<Funds> createFunds(@RequestBody Funds funds){
        Funds savedFunds = fundsRepository.save(funds);
        return new ResponseEntity<Funds>(savedFunds, HttpStatus.CREATED);
    }
}
