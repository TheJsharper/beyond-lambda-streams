package streams.operators;

import static lambda.comparator.utils.Utils.getMergedSimpleStudentAdresses;
import static lambda.comparator.utils.Utils.getSimpleStudents;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;

public class UsingMap {

	public static void main(String[] args) {
		var students = getSimpleStudents();
		print(getFirstNameUpperCase(students), "getFirstNameUpperCase");

		print(getSetOfLastNameUpperCase(students), "getSetOfLastNameUpperCase");

		print(getFirstNameLengthMap(students), "getFirstNameLengthMap");

		printAddresses(getAddressesUsingMapMulti(getMergedSimpleStudentAdresses().stream()), "AddressesUsingMapMulti");

		System.out.printf("AVG AGES Value: %s  %s\n", getAgeAverageUsingMapMultiToDouble(students.stream()).toString(),
				"getAgeAverageUsingMapMultiToDouble".toUpperCase());

		System.out.printf("MAX AGE Value: %s  %s\n", getAgeMaxUsingMapMultiToLong(students.stream()).toString(),
				"getAgeMaxUsingMapMultiToLong".toUpperCase());

		System.out.printf("MIN AGE Value: %s  %s\n", getAgeMinUsingMapMultiToInt(students.stream()).toString(),
				"getAgeMinUsingMapMultiToInt".toUpperCase());

	}

	private static List<String> getFirstNameUpperCase(List<Student> students) {
		return students.stream().map(Student::getFirstName).map(String::toUpperCase).collect(Collectors.toList());
	}

	private static Set<String> getSetOfLastNameUpperCase(List<Student> students) {
		return students.stream().map(Student::getLastName).map(String::toUpperCase).collect(Collectors.toSet());
	}

	private static Map<String, Integer> getFirstNameLengthMap(List<Student> students) {
		return students.stream().map(Student::getFirstName).collect(Collectors.toMap(String::toString, String::length));
	}

	private static List<Address> getAddressesUsingMapMulti(Stream<Student> students) {

		return students.mapMulti((Student s, Consumer<List<Address>> consumer) -> consumer.accept(s.getAddresses()))
				.flatMap(List::stream).collect(Collectors.toList());
	}

	private static Double getAgeAverageUsingMapMultiToDouble(Stream<Student> students) {
		var value = students.mapMultiToDouble((s, DoubleConsumer) -> {
			DoubleConsumer.accept(s.getAge());
		}).average();
		return value.isPresent() ? value.getAsDouble() : 0.0;
	}

	private static Long getAgeMaxUsingMapMultiToLong(Stream<Student> students) {
		var value = students.mapMultiToLong((Student s, LongConsumer longConsumer) -> {
			longConsumer.accept(s.getAge());
		}).max();
		return value.isPresent() ? value.getAsLong() : 0;
	}

	private static Integer getAgeMinUsingMapMultiToInt(Stream<Student> students) {
		var value = students.mapMultiToInt((Student s, IntConsumer longConsumer) -> {
			longConsumer.accept(s.getAge());
		}).min();
		return value.isPresent() ? value.getAsInt() : 0;
	}

	private static void print(Collection<String> names, String Label) {
		System.out.println("-------------------------" + Label.toUpperCase() + "----------------------");
		Consumer<String> consumer = (name) -> System.out.println(name);
		names.forEach(consumer);
	}

	private static void printAddresses(Collection<Address> names, String Label) {
		System.out.println("-------------------------" + Label.toUpperCase() + "----------------------");
		Consumer<Address> consumer = (name) -> System.out.println(name);
		names.forEach(consumer);
	}

	private static void print(Map<String, Integer> map, String Label) {
		System.out.println("-------------------------" + Label.toUpperCase() + "----------------------");
		BiConsumer<String, Integer> consumer = (name, lenght) -> System.out.println(name + " " + lenght);
		map.forEach(consumer);
	}

}
