package lambda.comparator.lambda.model;

import java.util.Objects;
import java.util.function.Consumer;

public class Address {

	public static Consumer<Address> TO_STRING2 = Address::getCountry;

	private int id;
	private String street;

	private String city;
	private String zip;

	public Address(int id, String street, String city, String zip, String country) {
		super();
		this.id = id;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.country = country;
	}

	public Address() {
	}

	private String country;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	
	
	@Override
	public int hashCode() {
		return Objects.hash(city, country, id, street, zip);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(city, other.city) && Objects.equals(country, other.country) && id == other.id
				&& Objects.equals(street, other.street) && Objects.equals(zip, other.zip);
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", street=" + street + ", city=" + city + ", zip=" + zip + ", country=" + country
				+ "]";
	}

}
