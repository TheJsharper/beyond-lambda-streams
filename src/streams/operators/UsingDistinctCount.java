package streams.operators;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class UsingDistinctCount {

	public static void main(String[] args) {
		var students = createStudentListWithAddresses();

		var merged = duplicateStudents(students);
		merged.sort((s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName()));
		print(getDistinctStudents(merged), "getDistinctStudents");
		System.out.print(String.format("%s %d %s\n", "---------------- After Merging List", merged.stream().count(),
				"------------"));
		System.out.print(String.format("%s %d %s\n", "---------------- Before Merging List ", students.stream().count(),
				"------------"));

	}

	private static List<Student> getDistinctStudents(List<Student> students) {

		return students.stream().distinct().collect(Collectors.toList());
	}

	private static List<Student> duplicateStudents(List<Student> students) {

		return Stream.concat(students.stream(), students.stream()).collect(Collectors.toList());
	}

	private static void print(List<Student> students, String label) {
		System.out.printf("----------------------- %s %s\n", label, "---------------------------------");
		students.forEach(UsingDistinctCount::printStudent);

	}

	private static void printStudent(Student student) {
		System.out.println(student);
	}

}
