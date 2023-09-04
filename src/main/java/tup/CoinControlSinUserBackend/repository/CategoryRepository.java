package tup.CoinControlSinUserBackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tup.CoinControlSinUserBackend.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    void deleteCategoryById(Long id);

    Optional<Category> findCategoryById(Long id);
    // : Este método declara una operación para buscar una entidad Category por su
    // ID. El resultado se envuelve en un Optional, lo que significa que el objeto
    // Category puede o no estar presente en la respuesta.
}
