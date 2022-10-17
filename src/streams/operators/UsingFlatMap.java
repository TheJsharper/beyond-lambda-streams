package streams.operators;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingFlatMap {

	public static void main(String[] args) {
		var students = Utils.getMergedSimpleStudentAdresses();
		print(getSimpleFlatMap());
		print(getCountries(students.stream()));
		printAddresses(getCountriesByName(students.stream(), "MX"));
	}

	private static List<String> getCountries(Stream<Student> students) {
		return students.map(Student::getAddresses).flatMap(List::stream).map(Address::getCountry).distinct().sorted()
				.collect(Collectors.toList());
	}

	private static List<Address> getCountriesByName(Stream<Student> students, String country) {

		return students.map((student) -> {
			var selectAddressesByCountry = student.getAddresses().stream().filter((s) -> s.getCountry().equals(country))
					.collect(Collectors.toList());
			student.setAddresses(selectAddressesByCountry);
			return student;
		}).filter((s) -> s.getAddresses().size() > 0).flatMap((s) -> {
			return s.getAddresses().stream();
		}).collect(Collectors.toList());

	}

	private static void print(Collection<String> students) {

		Consumer<String> consumer = System.out::println;
		students.forEach(consumer);
	}

	private static void printAddresses(Collection<Address> addresses) {
		Consumer<Address> consumer = System.out::println;
		addresses.forEach(consumer);
	}

	private static List<String> getSimpleFlatMap() {
		String[][] array = new String[][] { { "a", "b" }, { "c", "d" }, { "e", "f" } };
		return Stream.of(array).flatMap((String[] alphabets) -> Stream.of(alphabets)).collect(Collectors.toList());
	}

}
