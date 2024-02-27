package pc05401.assignment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.entity.Voucher;

public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
	List<Voucher> findByActiveTrue();
}
