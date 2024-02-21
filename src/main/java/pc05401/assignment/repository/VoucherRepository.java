package pc05401.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.entity.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

}
