package pc05401.assignment.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "staff")
public class Staff implements Serializable{
	@Id
	private int staffId;
	private String username;
	private String password;
	private String name;
	private boolean active;
	private String role;
	private double salary;
}
