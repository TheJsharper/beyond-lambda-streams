package lambda.comparator.lambda.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "Student")
public class Student implements Comparable<Student> {
	private Integer id;
	private String firstName;
	private String lastName;
	private int age;
	private String email;
	private String gender;
	private String ipAddress;

	private List<Address> addresses = new ArrayList<>();

	public Student() {
	}

	public Student(String firstName, String lastName, int age, String email, String gender, String ipAddress) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.email = email;
		this.gender = gender;
		this.ipAddress = ipAddress;
	}

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

	@Override
	public int compareTo(Student o) {

		return this.firstName.compareTo(o.firstName);
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

	public void printAddresses() {
		System.out.println("List of Addresses :" + this.addresses);
	}

	public void printFullName() {
		System.out.printf("Name %s %s %s\n", this.getFirstName(), " ", this.getLastName());
	};

}