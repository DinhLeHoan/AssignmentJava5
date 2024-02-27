package pc05401.assignment.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffModel {

	private int staffId;


	private String username;

	private String password;

	private String name;

	private boolean active;

	private String role;

	private double salary;
	
	private String phone;

	public StaffModel() {
		
	}
	
	public StaffModel(int staffId, String username, String password, String name, boolean active, String role,
			double salary, String phone) {
		super();
		this.staffId = staffId;
		this.username = username;
		this.password = password;
		this.name = name;
		this.active = active;
		this.role = role;
		this.salary = salary;
		this.phone = phone;
	}

	public int getStaffId() {
		return staffId;
	}

	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
