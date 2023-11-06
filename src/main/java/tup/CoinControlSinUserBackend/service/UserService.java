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

    // Metodo para crear un nuevo usuario
    // Toma un parametro de tipo user y devulve un User
    public User addUser(User user) {
        // Utilizamos el repositorio para llamar el metodo save, toma el objeto user
        // pasado por parametro y lo guarda en la base de datos
        return userRepository.save(user);
    }

    // Metodo para obtener todos los usuarios
    // Devulve una lista de tipo usuario
    public List<User> findAllUsers() {
        // Se utiliza el metodo findAll para obtener todos los User
        return userRepository.findAll();
    }

    // Metodo para actualizar un usuario
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

    // Metodo para buscar un Usuario por ID
    // Toma un parametro por id que es el usuario que se desea buscar
    // El metodo devuelve un objeto de tipo User
    public User findUserById(Long id) {
        // Se llama al repostirio para usar el metodo y se le pasa por parametros el id
        return userRepository.findUserById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id" + id + "was not found"));
    }

    // Metodo para eliminar un usuario por Id
    // Este metodo no devuelve nada y toma un parametro por id
    public void deleteUser(Long id) {
        // Se llama a el repositorio para usar el metodo para eliminar un usuario
        userRepository.deleteUserById(id);
    }
}
