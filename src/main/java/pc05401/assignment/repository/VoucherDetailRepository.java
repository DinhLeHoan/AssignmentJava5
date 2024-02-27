package pc05401.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pc05401.assignment.entity.Bill;
import pc05401.assignment.entity.Voucher;
import pc05401.assignment.entity.VoucherDetail;

public interface VoucherDetailRepository extends JpaRepository<VoucherDetail, Integer> {

    @Query("DELETE FROM VoucherDetail vd WHERE vd.bill.billId = :billId")
    @Modifying
    void deleteByBill_BillId(@Param("billId") int billId);

    @Query("SELECT vd FROM VoucherDetail vd WHERE vd.bill.billId = :billId")
    VoucherDetail findByBillId(@Param("billId") int billId);

    @Modifying
    @Query("UPDATE VoucherDetail vd SET vd.voucher = :voucher WHERE vd.bill.billId = :billId")
    void updateVoucherDetail(@Param("billId") int billId, @Param("voucher") Voucher voucher);

    void deleteByBill(Bill bill);
}
