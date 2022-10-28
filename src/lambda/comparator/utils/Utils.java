package lambda.comparator.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
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

	public static Stream<Student> buildStreamFromListStudent(List<Student> students) {
		Stream.Builder<Student> builder = Stream.builder();
		students.forEach(builder::add);
		return builder.build();

	}

	public static Function<Student, String> keyMapperByAgesGroupingString() {
		return (Student s) -> {
			if (s.getAge() <= 5) {
				return "CHILDHOOD";
			} else if (s.getAge() >= 6 && s.getAge() <= 10) {
				return "PRE_TEENAGER";
			} else if (s.getAge() >= 11 && s.getAge() <= 15) {
				return "TEENAGER";
			} else if (s.getAge() >= 16 && s.getAge() <= 20) {
				return "POST_TEENAGER";

			} else if (s.getAge() >= 21 && s.getAge() <= 25) {
				return "YOUNG_AGE";

			} else if (s.getAge() >= 26 && s.getAge() <= 30) {
				return "POST_YOUNG";

			} else if (s.getAge() >= 31 && s.getAge() <= 35) {
				return "JUVENILE_AGE";

			} else if (s.getAge() >= 36 && s.getAge() <= 40) {
				return "ADULT";

			} else if (s.getAge() >= 41 && s.getAge() <= 45) {
				return "MITTLE_ADULT";

			} else if (s.getAge() >= 46 && s.getAge() <= 50) {
				return "HIGHT_ADULT";

			} else if (s.getAge() >= 51 && s.getAge() <= 55) {
				return "OLD_ADULT";
			} else {
				return "OLD";
			}
		};
	}

	public static double getDistanceDiff(long a, long b) {
		double avg = (a + b) / 2;
		return 100 * (Math.abs(a - b) / avg);
	}

	public static Supplier<Stream<Student>> getStudentSupplier(Stream<Student> student) {
		return () -> student;
	}

	public static void print(Collection<String> names, String Label) {
		System.out.println("-------------------------" + Label.toUpperCase() + "----------------------");
		Consumer<String> consumer = (name) -> System.out.println(name);
		names.forEach(consumer);
	}

	public static void print(Map<String, List<Student>> studentMap, String label) {
		System.out.println("-------------------------" + label.toUpperCase() + "----------------------");
		studentMap.forEach((String key, List<Student> students) -> {
			System.out.println("==> Key: " + key);
			print(students, "-------------------------List of students ------------------------");
		});

	}

	public static void printMapAddresses(Map<String, List<Address>> studentMap, String label) {
		System.out.println("-------------------------" + label.toUpperCase() + "----------------------");
		studentMap.forEach((String key, List<Address> students) -> {
			System.out.println("==> Key: " + key);
			printAddresses(students, "-------------------------List of Addresses ------------------------");
		});

	}

	public static void printMapKeyInteger(Map<Integer, List<Student>> studentMap, String label) {
		System.out.println("-------------------------" + label.toUpperCase() + "----------------------");
		studentMap.forEach((Integer key, List<Student> students) -> {
			System.out.println("==> Key: " + key);
			print(students, "-------------------------List of students ------------------------");
		});

	}

	public static void printAddresses(Collection<Address> names, String Label) {
		System.out.println("-------------------------" + Label.toUpperCase() + "----------------------");
		Consumer<Address> consumer = (name) -> System.out.println(name);
		names.forEach(consumer);
	}

	public static void printListOfStudents(List<String> students, String label) {
		System.out.printf("----------------------- %s %s\n", label, "---------------------------------");
		students.forEach(Utils::printStudent);

	}

	public static void print(List<Student> students, String label) {
		System.out.printf("----------------------- %s %s\n", label, "---------------------------------");
		students.forEach(Utils::printStudent);
	}

	public static void printStudent(Student student) {
		System.out.println(student);
	}

	public static void printStudent(String student) {
		System.out.println(student);

	}

	public static void printOptional(Optional<Student> s, String labelIfPresent, String labelIfNotPresent) {
		s.ifPresentOrElse((Student student) -> System.out.printf("%s  %s\n", labelIfPresent, student),
				() -> System.err.print(labelIfNotPresent));
	}

}
