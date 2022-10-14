package lambda.comparator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;

public class Utils {

	public static List<Student> createStudentList() {
		ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.NONE)
				.setVisibility(PropertyAccessor.GETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.SETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.CREATOR, Visibility.PUBLIC_ONLY);
		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Student.class);

		File initialFile = new File("resources/persons.json");
		List<Student> students = new ArrayList<>();
		try {
			InputStream targetStream = new FileInputStream(initialFile);
			students = mapper.readValue(targetStream, collectionType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return students;
	}

	public static List<Address> createAddressList() {
		ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.NONE)
				.setVisibility(PropertyAccessor.GETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.SETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.CREATOR, Visibility.PUBLIC_ONLY);
		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Address.class);

		File initialFile = new File("resources/address.json");
		List<Address> addresses = new ArrayList<>();
		try {
			InputStream targetStream = new FileInputStream(initialFile);
			addresses = mapper.readValue(targetStream, collectionType);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return addresses;
	}

	public static List<Student> createStudentListWithAddresses() {
		var students = createStudentList();
		var addresses = createAddressList();
		Supplier<Stream<List<Address>>> supplier = () -> Stream.of(addresses);

		Map<String, List<Address>> addressMap = supplier.get().findFirst().get().stream()
				.collect(Collectors.groupingBy(Address::getCountry));
		List<String> keys = new ArrayList<>(addressMap.keySet());
		Random next = new Random();
		return students.stream().map((Student s) -> {
			int index = next.nextInt(0, keys.size());
			String nextCountryKey = keys.get(index);
			s.setAddresses(addressMap.get(nextCountryKey));
			return s;
		}).collect(Collectors.toList());

	}
	@SuppressWarnings("serial")
	public static List<Student> getSimpleStudents() {

		var students = new ArrayList<Student>() {

			{
				add(new Student("Max", "Bauer", 20, "max.bauer@domain.com", "male", null));
				add(new Student("Min", "Schmitz", 24, "min.schmitz@domain.com", "male", null));
				add(new Student("Linda", "Berger", 22, "linda.berger@domain.com", "female", null));
				add(new Student("Rosa", "Maier", 15, "rosa.maier@maydomain.com", "female", null));
			}
		};
		return students;
	}

	@SuppressWarnings("serial")
	public static List<Address> getSimpleAddresses() {
		return new ArrayList<Address>() {
			{
				add(new Address(1, "Mainstreet,56", "New York", "7852NY8588", "USA"));
				add(new Address(2, "Mainstreet,85", "Los Angeles", "3692LA989", "MX"));
				add(new Address(3, "Mainstreet,78", "Chicago", "7852CH8588", "USA"));
				add(new Address(4, "Mainstreet,102", "Miami", "7852FL8588", "USA"));
			}
		};
	}

	public static List<Student> getMergedSimpleStudentAdresses() {

		var students = getSimpleStudents();
		var addresses = getSimpleAddresses();

		Random next = new Random();
		var result = students.stream().collect(Collectors.mapping((Student s) -> {
			int index = next.nextInt(0, addresses.size());
			var subList = addresses.subList(0, index);
			s.setAddresses(subList);
			return s;
		}, Collectors.toList()));

		return result;
	}


}
