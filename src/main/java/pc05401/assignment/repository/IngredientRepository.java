package pc05401.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

}
