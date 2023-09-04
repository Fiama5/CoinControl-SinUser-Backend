package tup.CoinControlSinUserBackend.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import tup.CoinControlSinUserBackend.model.Category;
import tup.CoinControlSinUserBackend.repository.CategoryRepository;
import tup.CoinControlSinUserBackend.service.NotFoundException.CategoryNotFoundException;



@Service
public class CategoryService {
        private final CategoryRepository categoryRepository;

        @Autowired
        public CategoryService(CategoryRepository categoryRepository) {
            this.categoryRepository = categoryRepository;
        }
        

        //Agregar Nueva Categoría
        public Category addCategory(Category category) {
            return categoryRepository.save(category);
        }
    
        //Mostrar todas las categorias
        public List<Category> findAllCategories() {
            return categoryRepository.findAll();
        }
    
        //Actualizar una Categoria
        public Category updateCategory(Category category) {
            return categoryRepository.save(category);
        }
    
        //Mostrar categoria por id
        public Category findCategoryById(Long id) {
            return categoryRepository.findCategoryById(id)
                    .orElseThrow(() -> new CategoryNotFoundException("Category by id" + id + "was not found"));
        }
    
        //Borrar una categoria por id
        @Transactional
        public void deleteCategory(Long id) {
            categoryRepository.deleteCategoryById(id);
        }
}
