package streams.operators;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingFlatMap {

	public static void main(String[] args) {
		var students = Utils.getMergedSimpleStudentAdresses();
		Supplier<Stream<Student>> supplier = () -> {
			return students.stream();
		};
		print(getSimpleFlatMap());
		print(getCountries(supplier.get().findFirst().stream()));
		printAddresses(getCountriesByName(supplier.get().findFirst().stream(), "MX"));
		
		System.out.printf("%g  %s\n", getMaxAddressOfStudent(supplier.get().findFirst().stream()),
				"getMaxAddressOfStudent".toUpperCase());
		System.out.printf("%d  %s\n", getMinAddressOfStudent(supplier.get().findFirst().stream()),
				"getMinAddressOfStudent".toUpperCase());
		System.out.printf("%d  %s\n", getAvgAddressOfStudent(supplier.get().findFirst().stream()),
				"getAvgAddressOfStudent".toUpperCase());
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

	private static double getMaxAddressOfStudent(Stream<Student> students) {

		var maxValue = students.map((s) -> s.getAddresses()).distinct()
				.flatMapToDouble((List<Address> s) -> DoubleStream.of(s.size())).max();

		return maxValue.isPresent() ? maxValue.getAsDouble() : 0.0;

	}

	private static int getMinAddressOfStudent(Stream<Student> students) {

		var minValue = students.map((s) -> s.getAddresses()).distinct()
				.flatMapToInt((List<Address> s) -> IntStream.of(s.size())).max();

		return minValue.isPresent() ? minValue.getAsInt() : 0;

	}

	private static Long getAvgAddressOfStudent(Stream<Student> students) {

		var avgValue = students.map((s) -> s.getAddresses()).distinct()
				.flatMapToLong((List<Address> s) -> LongStream.of(s.size())).max();

		return avgValue.isPresent() ? avgValue.getAsLong() : 0L;

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
