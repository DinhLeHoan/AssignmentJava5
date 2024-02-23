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
}
