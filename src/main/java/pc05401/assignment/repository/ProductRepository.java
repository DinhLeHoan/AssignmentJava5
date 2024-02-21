package pc05401.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
