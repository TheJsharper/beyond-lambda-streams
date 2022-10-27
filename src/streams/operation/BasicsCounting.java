package streams.operation;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class BasicsCounting {

	public static void main(String[] args) {

		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());
		
		System.out.println("-> Result: " + counting(s.get()));

		System.out.println("-> Result: " + countingWithSomeFilter(s.get()));
	}

	private static long counting(Stream<Student> students) {

		return students.collect(Collectors.counting());

	}

	private static long countingWithSomeFilter(Stream<Student> students) {

		Predicate<Student> condition = (Student s) -> s.getAge() >= 18;

		return students.filter(condition).collect(Collectors.counting());

	}
}
