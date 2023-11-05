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
    public ResponseEntity<Funds> createOrUpdateFunds(@RequestBody Funds funds) {
        // Buscar el fondo existente para el usuario y el predefinedFund
        // Se utiliza el objeto fundsRepository para buscar en la base de datos si ya
        // existe un registro de fundsUser y el PredefinedFund
        // especificados en el objeto funds recibido en la solicitud.
        Funds existingFunds = fundsRepository.findByUserAndPredefinedFund(funds.getUser(), funds.getPredefinedFund());

        if (existingFunds != null) {
            // Si existingFunds no es nulo, siginifica que se encontro un registro en
            // fundsRepository
            // Si existe, actualizo el monto
            existingFunds.setAmount(existingFunds.getAmount() + funds.getAmount());
            // Se actualiza el monto existente sumándole el monto del objeto funds recibido
            // en la solicitud.
            Funds updatedFunds = fundsRepository.save(existingFunds);
            // Se guarda el monto en los fondos existentes
            return new ResponseEntity<>(updatedFunds, HttpStatus.OK);
        } else {
            // Si no existe, se crea un nuevo registo
            Funds savedFunds = fundsRepository.save(funds);
            return new ResponseEntity<>(savedFunds, HttpStatus.CREATED);
        }
    }

    // Endpoint para obtener todos los fondos
    @GetMapping("/all")
    public ResponseEntity<List<Funds>> getAllFunds() {
        // Obtiene todos los fondos a través del servicio de fondos
        List<Funds> funds = fundsService.findAllFunds();

        // Retorna una respuesta con la lista de todos los fondos
        return new ResponseEntity<>(funds, HttpStatus.OK);
    }

    // Endpoint para encontrar fondos por ID de usuario
    @GetMapping("/find/user/{userId}")
    public ResponseEntity<List<Funds>> getFundsByUser(@PathVariable Long userId) {
        // Busca los fondos en el repositorio según el ID de usuario proporcionado
        List<Funds> funds = fundsRepository.findByUserId(userId);

        // Retorna una respuesta con la lista de fondos asociados al usuario y el código
        // de estado HTTP OK (200)
        return new ResponseEntity<>(funds, HttpStatus.OK);
    }

}
