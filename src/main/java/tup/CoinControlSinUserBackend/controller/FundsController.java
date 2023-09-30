package tup.CoinControlSinUserBackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tup.CoinControlSinUserBackend.model.Funds;
import tup.CoinControlSinUserBackend.repository.FundsRepository;
import tup.CoinControlSinUserBackend.service.FundsService;

@RestController
@RequestMapping("/funds")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class FundsController {

    private final FundsRepository fundsRepository;
    private final FundsService fundsService;

    @Autowired
    public FundsController(FundsRepository fundsRepository, FundsService fundsService) {
        this.fundsRepository = fundsRepository;
        this.fundsService = fundsService;
    }

    @PostMapping("/add")
    public ResponseEntity<Funds> createFunds(@RequestBody Funds funds) {
        Funds savedFunds = fundsRepository.save(funds);
        return new ResponseEntity<Funds>(savedFunds, HttpStatus.CREATED);
    }

    // Traer todos los fondos
    @GetMapping("/all")
    public ResponseEntity<List<Funds>> getAllFunds() {
        List<Funds> funds = fundsService.findAllFunds();
        return new ResponseEntity<>(funds, HttpStatus.OK);
    }

    @GetMapping("/find/user/{userId}")
    public ResponseEntity<List<Funds>> getFundsByUser(
            @PathVariable Long userId) {
        List<Funds> funds = fundsRepository.findByUserId(userId);
        return new ResponseEntity<>(funds, HttpStatus.OK);
    }

}
