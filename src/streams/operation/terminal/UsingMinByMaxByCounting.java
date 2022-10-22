package streams.operation.terminal;

import static lambda.comparator.utils.Utils.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class UsingMinByMaxByCounting {

	public static void main(String[] args) {

		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		System.out.println("Count: " + getStudentCountByAgeTerminalOperationCounting(s.get(), 20));

		System.out.println("Count: " + getStudentCountByAgeStreamsCount(s.get(), 20));

		System.out.println("Count: " + getStudentCountByFirstNamStartsWith(s.get(), "z"));

		System.out.println();
		
		printOptional(getStudentMaxAge(s.get()), "Student max age: ", " Not Found Student with max age");
		
		System.out.println();

		print(getStudentMaxAges(s), "-------------------List of student with max Age-------------------");
		
		System.out.println();
		
		printOptional(getStudentMinAge(s.get()), "Student min age: ", " Not Found Student with min age");

		System.out.println();
		
		print(getStudentMinAges(s), "-------------------List of student with max Age-------------------");

	}

	private static long getStudentCountByAgeTerminalOperationCounting(Stream<Student> students, int age) {
		return students.filter((Student s) -> s.getAge() == age).collect(Collectors.counting());
	}

	private static long getStudentCountByAgeStreamsCount(Stream<Student> students, int age) {
		return students.filter((Student s) -> s.getAge() == age).count();
	}

	private static long getStudentCountByFirstNamStartsWith(Stream<Student> students, String startsWitch) {
		return students.filter((Student s) -> s.getFirstName().toUpperCase().startsWith(startsWitch.toUpperCase()))
				.collect(Collectors.counting());
	}

	private static Optional<Student> getStudentMaxAge(Stream<Student> students) {
		return students.collect(Collectors.maxBy(Comparator.comparing(Student::getAge)));
	}

	private static List<Student> getStudentMaxAges(Supplier<Stream<Student>> supplier) {

		Optional<Student> maxStudent = supplier.get().collect(Collectors.maxBy(Comparator.comparing(Student::getAge)));
		return maxStudent.isPresent() ? supplier.get().filter((Student s) -> s.getAge() == maxStudent.get().getAge())
				.collect(Collectors.toList()) : new ArrayList<Student>();
	}

	private static Optional<Student> getStudentMinAge(Stream<Student> students) {
		return students.collect(Collectors.minBy(Comparator.comparing(Student::getAge)));
	}

	private static List<Student> getStudentMinAges(Supplier<Stream<Student>> supplier) {
		Optional<Student> minStudent = supplier.get().collect(Collectors.minBy(Comparator.comparing(Student::getAge)));
		return minStudent.isPresent() ? supplier.get().filter((Student s) -> s.getAge() == minStudent.get().getAge())
				.collect(Collectors.toList()) : new ArrayList<Student>();
	}

}
