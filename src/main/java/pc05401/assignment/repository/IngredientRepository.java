package pc05401.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import pc05401.assignment.entity.Ingredient;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

	@Transactional
	@Modifying
	@Query("UPDATE Ingredient i SET i.amount = i.amount + 1 WHERE i.ingredientId = :ingredientId")
	void addOneToAmount(@Param("ingredientId") int ingredientId);

	@Transactional
	@Modifying
	@Query("UPDATE Ingredient i SET i.amount = i.amount - 1 WHERE i.ingredientId = :ingredientId")
	void subtractOneFromAmount(@Param("ingredientId") int ingredientId);
}
