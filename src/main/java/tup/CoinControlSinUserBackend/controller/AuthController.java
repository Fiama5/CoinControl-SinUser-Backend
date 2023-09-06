package tup.CoinControlSinUserBackend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tup.CoinControlSinUserBackend.model.User;
import tup.CoinControlSinUserBackend.repository.UserRepository;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class AuthController {
     @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userRepository.findByUsername(user.getUsername());
        
        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            // Usuario y contraseña coinciden
            return ResponseEntity.ok().body("{\"message\": \"Inicio de sesión exitoso\"}");
        } else {
            // Usuario y contraseña no coinciden o el usuario no existe
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales incorrectas\"}");
        }
    }
    
}