package tup.CoinControlSinUserBackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/* Imports para metodos no utilizados
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
*/

import tup.CoinControlSinUserBackend.model.Category;
import tup.CoinControlSinUserBackend.service.CategoryService;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" })
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Traer todas las categorías
    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        // Llama al servicio para obtener todas las categorías
        List<Category> categories = categoryService.findAllCategories();

        // Devuelve una respuesta con la lista de categorías obtenidas
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    // Traer categoría por id
    @GetMapping("/find/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") Long id) {
        // Obtiene la categoría utilizando el ID proporcionado
        Category category = categoryService.findCategoryById(id);

        // Retorna una respuesta con la categoría encontrada
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    /*
     * Metodos no utilizados
     * // Crear nueva categoría
     * 
     * @PostMapping("/add")
     * public ResponseEntity<Category> addCategory(@RequestBody Category category) {
     * Category newCategory = categoryService.addCategory(category);
     * return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
     * }
     * 
     * // Actualizar categoría
     * 
     * @PutMapping("/update")
     * public ResponseEntity<Category> updateCategory(@RequestBody Category
     * category) {
     * Category updatedCategory = categoryService.updateCategory(category);
     * return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
     * }
     * 
     * // Eliminar una categoría
     * 
     * @DeleteMapping("/delete/{id}")
     * public ResponseEntity<?> deleteCategory(@PathVariable("id") Long id) {
     * categoryService.deleteCategory(id);
     * return new ResponseEntity<>(HttpStatus.OK);
     * }
     * 
     */

}
