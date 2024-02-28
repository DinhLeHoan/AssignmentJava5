package pc05401.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.entity.TagProduct;

public interface TagProductRepository extends JpaRepository<TagProduct, Integer> {
	List<TagProduct> findByActiveTrue();
}
