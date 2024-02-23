package pc05401.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import pc05401.assignment.entity.Bill;

public interface BillRepository extends JpaRepository<Bill, Integer> {

	List<Bill> findAllByIsPaidFalse();

	@Transactional
	@Modifying
	@Query("UPDATE Bill b SET b.isPaid = true WHERE b.billId = :billId")
	void markBillAsPaid(@Param("billId") int billId);

	@Transactional
	@Modifying
	@Query("DELETE FROM Bill b WHERE b.billId = :billId")
	void deleteBillById(@Param("billId") int billId);
}
