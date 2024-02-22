package pc05401.assignment.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.entity.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
	Staff findByUsernameAndPassword(String username, String password) ;
	
	Staff findByUsername(String username) ;
	
	Staff findByPhone(String phone) ;
	
	Optional<Staff> findById(Integer staffId);
}
