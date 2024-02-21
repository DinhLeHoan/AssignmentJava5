package pc05401.assignment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import pc05401.assignment.model.Staff;

public interface StaffDAO extends JpaRepository<Staff, Integer>{
}
