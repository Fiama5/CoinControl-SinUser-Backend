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

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> user = userService.findAllUsers();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User newUser= userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/upadte")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User updatUser = userService.updateUser(user);
        return new ResponseEntity<User>(updatUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}