package streams.operators;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.List;
import java.util.Optional;

import lambda.comparator.lambda.model.Student;

public class UsingFindAnyFindFirst {

	public static void main(String[] args) {
		var students = createStudentListWithAddresses();

		printStudentOptional(getSimpleFirstStudent(students));

		printStudentOptional(getSimpleAnyStudent(students));

		printStudentOptional(getFirstStudentFirstNameStartsWitch(students, "A"));

		printStudentOptional(getFirstStudentFirstNameStartsWitch(students, "ItDoestNotExist"));

		printStudentOptional(getAnyStudentLastNameStartsWitch(students, "Z"));

		printStudentOptional(getAnyStudentLastNameStartsWitch(students, "ItDoestNotExist"));

	}

	

	private static Optional<Student> getSimpleFirstStudent(List<Student> students) {
		return students.stream().findFirst();
	}

	private static Optional<Student> getSimpleAnyStudent(List<Student> students) {
		return students.stream().findAny();
	}

	private static Optional<Student> getFirstStudentFirstNameStartsWitch(List<Student> students, String startsWitch) {
		return students.stream().filter((Student s) -> s.getFirstName().startsWith(startsWitch)).findFirst();
	}

	private static Optional<Student> getAnyStudentLastNameStartsWitch(List<Student> students, String startsWitch) {
		return students.stream().filter((Student s) -> s.getFirstName().startsWith(startsWitch)).findAny();
	}

	private static void printStudentOptional(Optional<Student> student) {
		student.ifPresentOrElse((Student s) -> {
			printStudent(s);
		}, () -> System.err.println("It does not found any student"));
	}

	private static void printStudent(Student student) {
		System.out.println(student);
		if (student.getAddresses() != null) {
			System.out.println(student.getAddresses());
		}
	}

}
