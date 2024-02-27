package pc05401.assignment.repository;

import java.util.List;
import java.util.Optional;

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

	@Transactional
	@Modifying
	@Query("UPDATE Bill b SET b.total = b.total + :price WHERE b.billId = :billId")
	void updateTotalByAddingPrice(@Param("billId") int billId, @Param("price") double price);

	@Transactional
	@Modifying
	@Query("UPDATE Bill b SET b.totalWithVoucher = b.totalWithVoucher + :price WHERE b.billId = :billId")
	void updateTotalWithVoucherByAddingPrice(@Param("billId") int billId, @Param("price") double price);

	@Transactional
	@Modifying
	@Query("UPDATE Bill b SET b.total = b.total - :price WHERE b.billId = :billId")
	void updateTotalBySubtractingPrice(@Param("billId") int billId, @Param("price") double price);

	@Transactional
	@Modifying
	@Query("UPDATE Bill b SET b.totalWithVoucher = b.totalWithVoucher - :price WHERE b.billId = :billId")
	void updateTotalWithVoucherBySubtractingPrice(@Param("billId") int billId, @Param("price") double price);

	@Query("SELECT DISTINCT bd.bill.billId FROM BillDetail bd WHERE bd.bill.date = CURRENT_DATE")
	List<Integer> findBillIdsToday();
	
	@Query("SELECT bd.product.productId, COUNT(bd.product) as Count FROM BillDetail bd WHERE bd.bill.billId = :billIdToday GROUP BY bd.product")
	List<Integer[]> findProductsAndCountItById(int billIdToday);
	
	@Query("SELECT b.total FROM Bill b WHERE b.billId = :billId")
    double findPriceById(@Param("billId") int billId);
	
	Optional<Bill> findById(int billId);
}
