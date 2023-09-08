package tup.CoinControlSinUserBackend.controller;

import java.util.HashMap;
import java.util.Map;

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
        // Se trata de encontrar un usuario en UserRepository con el mismo username que
        // User

        if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
            // Se verifica que se haya encontrado un usuario con ese username, si no es nulo
            // significa que se encontro un usuario con el nombre de usuario en la base de
            // datos
            // Si se encontro se compara la contraseña almacenada en la base de datos
            // (existingUser.getPassword()) con la contraseña proporcionada por el usuario
            // que intenta iniciar sesión (user.getPassword())

            Long userId = existingUser.getId();
            // Incluir el ID del usuario en la respuesta JSON
            Map<String, Object> response = new HashMap<>();
            // Un Map es una colección de datos que te permite asociar valores con claves
            // únicas para una búsqueda y acceso eficientes.
            response.put("message", "Inicio de sesión exitoso");
            response.put("userId", userId);
            // las claves son cadenas de texto, como "message" y "userId", y los valores son
            // los mensajes y el ID de usuario que se incluyen en la respuesta JSON.
            return ResponseEntity.ok().body(response);
        } else {
            // Usuario y contraseña no coinciden o el usuario no existe
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales incorrectas\"}");
        }
    }

}