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

public interface RevenueRespository extends JpaRepository<BillDetail, Integer> {
	List<BillDetail> findByBill_BillId(int billId);

	@Query("SELECT bd.product, COUNT(bd.product) FROM BillDetail bd WHERE bd.bill.billId = :billId GROUP BY bd.product")
	List<Object[]> findProductCountsByBillId(@Param("billId") int billId);

}
