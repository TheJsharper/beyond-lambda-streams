package streams.operators;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class UsingSorted {

	public static void main(String[] args) {

		var students = createStudentListWithAddresses();

		Supplier<Stream<Student>> supplier = () -> students.stream();

		print(getStudentSortingByFirstName(supplier.get()), "getStudentSortingByFirstName".toUpperCase());

		print(getStudentSortingByLastName(supplier.get()), "getStudentSortingByLastName".toUpperCase());

		print(getStudentSortingByEmail(supplier.get()), "getStudentSortingByEmail".toUpperCase());

		print(getStudentSortingByGender(supplier.get()), "getStudentSortingByGender".toUpperCase());
		
		print(getStudentSortingIpAddress(supplier.get()), "getStudentSortingByGender".toUpperCase());

	}

	private static List<Student> getStudentSortingByFirstName(Stream<Student> students) {
		return students.sorted(Comparator.comparing(Student::getFirstName)).collect(Collectors.toList());

	}

	private static List<Student> getStudentSortingByLastName(Stream<Student> students) {
		return students.sorted(Comparator.comparing(Student::getLastName)).collect(Collectors.toList());

	}

	private static List<Student> getStudentSortingByEmail(Stream<Student> students) {
		return students.sorted(Comparator.comparing(Student::getEmail)).collect(Collectors.toList());

	}

	private static List<Student> getStudentSortingByGender(Stream<Student> students) {
		return students.sorted(Comparator.comparing(Student::getGender)).collect(Collectors.toList());

	}

	private static List<Student> getStudentSortingIpAddress(Stream<Student> students) {
		return students.sorted(Comparator.comparing(Student::getIpAddress)).collect(Collectors.toList());

	}

	private static void print(List<Student> students, String label) {
		System.out.printf("----------------------- %s %s\n", label, "---------------------------------");
		students.forEach(UsingSorted::printStudent);
	}

	private static void printStudent(Student student) {
		System.out.println(student);
	}

}
