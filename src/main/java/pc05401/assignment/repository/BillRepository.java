package pc05401.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {

}
