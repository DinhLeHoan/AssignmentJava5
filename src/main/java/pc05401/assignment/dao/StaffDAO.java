package pc05401.assignment.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pc05401.assignment.entity.Staff;
import pc05401.assignment.model.StaffModel;
import pc05401.assignment.repository.StaffRepository;


public class StaffDAO {

	public StaffModel convertToModel(Staff staff) {
		StaffModel staffModel = new StaffModel();
		staffModel.setActive(staff.isActive());
		staffModel.setName(staff.getName());
		staffModel.setPassword(staff.getPassword());
		staffModel.setPhone(staff.getPhone());
		staffModel.setRole(staff.getRole());
		staffModel.setSalary(staff.getSalary());
		staffModel.setStaffId(staff.getStaffId());
		staffModel.setUsername(staff.getUsername());
		System.out.println(staffModel.getName());
		return staffModel;
	}

}
