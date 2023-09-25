package tup.CoinControlSinUserBackend.datainitializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import tup.CoinControlSinUserBackend.model.Category;
import tup.CoinControlSinUserBackend.repository.CategoryRepository;

@Component
public class CategoryInitializer implements CommandLineRunner{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryInitializer(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }



    @Override
    public void run(String... args) {
        if(categoryRepository.count()==0){
 Category category1 = new Category();
        category1.setName("Alimentos");
        categoryRepository.save(category1);

           Category category2 = new Category();
        category2.setName("Transporte");
        categoryRepository.save(category2);

        Category category3 = new Category();
        category3.setName("Impuestos");
        categoryRepository.save(category3);

        Category category4 = new Category();
        category4.setName("Educación");
        categoryRepository.save(category4);

        Category category5 = new Category();
        category5.setName("Salud");
        categoryRepository.save(category5);

        Category category6 = new Category();
        category6.setName("Vivienda");
        categoryRepository.save(category6);

        Category category7 = new Category();
        category7.setName("Entretenimiento");
        categoryRepository.save(category7);

        Category category8 = new Category();
        category8.setName("Ropa y Artículos Personales");
        categoryRepository.save(category8);
                System.out.println("Categorías creadas y guardadas en la base de datos.");
        } else {
            System.out.println("Las categorías ya existen en la base de datos. No se crearon nuevamente.");
        }

    }
}
