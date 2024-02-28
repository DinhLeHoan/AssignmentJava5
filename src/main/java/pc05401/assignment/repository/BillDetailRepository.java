package pc05401.assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import pc05401.assignment.entity.Bill;
import pc05401.assignment.entity.BillDetail;
import pc05401.assignment.entity.Product;

public interface BillDetailRepository extends JpaRepository<BillDetail, Integer> {
	List<BillDetail> findByBill_BillId(int billId);

	@Query("SELECT bd.product, COUNT(bd.product) FROM BillDetail bd WHERE bd.bill.billId = :billId GROUP BY bd.product")
	List<Object[]> findProductCountsByBillId(@Param("billId") int billId);

	BillDetail findFirstByBill_BillIdAndProduct_ProductId(int billId, int productId);

	@Modifying
	@Transactional
	@Query("INSERT INTO BillDetail(bill, product) SELECT b, p FROM Bill b JOIN Product p WHERE b.billId = :billId AND p.productId = :productId")
	void insertProductAmount(@Param("billId") int billId, @Param("productId") int productId);

	
	
	@Transactional
	void deleteByBill_BillIdAndProduct_ProductId(int billId, int productId);

	void deleteByBill_BillId(int billId);

	void deleteByBill(Bill bill);
	

}
