package tup.CoinControlSinUserBackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tup.CoinControlSinUserBackend.model.PredefinedFund;
import tup.CoinControlSinUserBackend.service.PredefinedFundService;

@RestController
@RequestMapping("/predefinedFund")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class PredefinedFundController {
    private final PredefinedFundService predefinedFundService;

    public PredefinedFundController(PredefinedFundService predefinedFundService) {
        this.predefinedFundService = predefinedFundService;
    }

    // Endpoint para obtener todos los fondos predefinidos
    @GetMapping("/all")
    public ResponseEntity<List<PredefinedFund>> getAllPredefinedFund() {
        // Obtiene todos los fondos predefinidos a través del servicio de fondos
        // predefinidos
        List<PredefinedFund> predefinedFunds = predefinedFundService.findAllPredefinedFund();

        // Retorna una respuesta con la lista de todos los fondos predefinidos
        return new ResponseEntity<>(predefinedFunds, HttpStatus.OK);
    }

}
