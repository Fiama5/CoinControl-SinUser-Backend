package tup.CoinControlSinUserBackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tup.CoinControlSinUserBackend.model.User;
import tup.CoinControlSinUserBackend.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Controller para obtener todos los usuarios
    @GetMapping("/all")
    // Devuelve un ResponseEntity que contiene una lista de objetos User
    public ResponseEntity<List<User>> getAllUser() {
        // se está llamando al método findAllUsers() del servicio userService para
        // recuperar todos los usuarios existentes
        // El resultado se guarda en una lista de objetos User llamada user.
        List<User> user = userService.findAllUsers();

        // Se crea un nuevo ResponseEntity que contendrá la lista de usuarios user
        // recuperada del servicio. Se especifica el código de estado HTTP
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Controller para buscar usuario por id
    @GetMapping("/find/{id}")
    // Espera recibir un parametro id, este valor se convierte en un Long y se
    // utiliza para buscar al usuario correspondiente en la base de datos.
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        // Se invoca el método findUserById(id) del servicio userService.
        // Este metodo busca en una base de datos al usuario que tenga el id pasado por
        // parametros y lo devuelve como un objeto User
        User user = userService.findUserById(id);
        // Se crea un objeto ResponseEntity que contendrá el objeto user (que contiene
        // la información del usuario buscado). Y se muestra el código de estado HTTP
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // Controller para crear un nuevo usuario
    @PostMapping("/add")
    // El metodo addUser espera un parametro de user de tipo User que se envia en el
    // cuepor de la solicitud
    public ResponseEntity<User> addUser(@RequestBody User user) {
        // Se invoca el método addUser(user) del servicio userService.
        // Este metodo se encarga de agregar un nuevo usuario a la base de datos
        // utilizando la informacion proporcionada en el objeto user y devuelve el
        // usuario recien agregado
        User newUser = userService.addUser(user);
        // Se crea un objeto ResponseEntity que contendrá el objeto newUser (el usuario
        // recién agregado). El código de estado HTTP se establece en HttpStatus.CREATED
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Controller para actualizar un usuario
    @PutMapping("/upadte")
    // El método updateUser espera un parámetro user del tipo User, que se espera
    // que se envíe en el cuerpo (body) de la solicitud HTTP
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        // Se invoca el método updateUser(user) del servicio userService.
        // Este metodo se encarga de actualizar la informacion del usuario en la base de
        // datos utilizando la informacion proporcionada en el objeto user y devuelve el
        // usuario actualizado
        User updatUser = userService.updateUser(user);
        // Se crea un objeto ResponseEntity que contendrá el objeto updatedUser (el
        // usuario actualizado). El código de estado HTTP se establece en HttpStatus.OK
        return new ResponseEntity<User>(updatUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}