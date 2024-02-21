package pc05401.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.entity.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {

}
