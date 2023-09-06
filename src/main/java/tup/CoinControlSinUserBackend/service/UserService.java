package tup.CoinControlSinUserBackend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tup.CoinControlSinUserBackend.model.User;
import tup.CoinControlSinUserBackend.repository.UserRepository;
import tup.CoinControlSinUserBackend.service.NotFoundException.UserNotFoundException;

@Service
public class UserService {
        private final UserRepository userRepository;


@Autowired
    public UserService(UserRepository userRepository) {
            this.userRepository = userRepository;
        }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
        // toma un argumento de tipo User (el cual es el usuario que se quiere
        // actualizar) y devuelve un objeto de tipo User, que representa el usuario
        // actualizado.
        // se utiliza un objeto llamado userRepository para llamar al método
        // save(user) en él. Esto implica que hay un componente o clase llamada
        // UserRepository que tiene un método save() para guardar o actualizar el
        // objeto user pasado como argumento. La llamada a este método guarda o
        // actualiza el objeto user en la de base de datos y luego devuelve el objeto
        // actualizado.

    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id" + id + "was not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteUserById(id);
    }
}
