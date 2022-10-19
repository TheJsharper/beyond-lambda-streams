package streams.operators;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.List;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.Student;

public class UsingFilter {

	public static void main(String[] args) {
		var students = createStudentListWithAddresses();

		print(getStudentByGender(students, "female"), "getStudentByGender");

		print(getStudentByLastNameStartsWith(students, "Z"), "getStudentByLastNameStartsWith");

		print(getStudentByCountry(students, "Austria"), "getStudentByCountry");
	}

	private static List<Student> getStudentByGender(List<Student> students, String gender) {
		return students.stream().filter((Student s) -> s.getGender().toUpperCase().equals(gender.toUpperCase()))
				.collect(Collectors.toList());
	}

	private static List<Student> getStudentByLastNameStartsWith(List<Student> students, String lastName) {
		return students.stream().filter((Student s) -> s.getLastName().toUpperCase().startsWith(lastName.toUpperCase()))
				.collect(Collectors.toList());
	}

	private static List<Student> getStudentByCountry(List<Student> students, String country) {
		return students.stream().filter((s) -> {
			var countries = s.getAddresses().stream()
					.filter((a) -> a.getCountry().toUpperCase().equals(country.toUpperCase()))
					.collect(Collectors.toList());

			s.setAddresses(countries);
			return countries.size() > 0 ? true : false;
		}).collect(Collectors.toList());

	}

	private static void print(List<Student> students, String label) {
		System.out.printf("----------------------- %s %s\n", label, "---------------------------------");
		students.forEach(UsingFilter::printStudent);

	}

	private static void printStudent(Student student) {
		System.out.println(student);
		if (student.getAddresses() != null) {
			System.out.println(student.getAddresses());
		}
	}
}
