package pc05401.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import pc05401.assignment.entity.Product;
import pc05401.assignment.entity.TagProduct;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	List<Product> findByTag(TagProduct tag);
	
	List<Product> findByActiveTrue() ;
	
	@Query("SELECT p FROM Product p WHERE p.active = true AND p.tag.active = true")
    List<Product> findActiveProductsWithActiveTag();

}
