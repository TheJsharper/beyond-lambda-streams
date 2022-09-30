package test.comparator.lambada;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value ="Student")
public class Student {
	private Integer id;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String gender;
	private String ipAddress;

	public Student() {
		super();
	}

	/*public Student(String firstName, String lastName, int age, String email, String gender, String ipAddress) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.gender = gender;
		this.ipAddress = ipAddress;
	}*/

	
	public String getFirstName() {
		return firstName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "Student [firstName=" + firstName + ", lastName=" + lastName + ", age=" + age + ", email=" + email
				+ ", gender=" + gender + ", ipAddress=" + ipAddress + "]";
	}

}