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
		Supplier<Stream<List<Address>>> supplier = () -> {
			return Stream.of(addresses);
		};

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

}
