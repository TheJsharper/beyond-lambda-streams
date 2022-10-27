package streams.operation;

import static lambda.comparator.utils.Utils.*;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class BasicsMinMaxBy {

	public static void main(String[] args) {

		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		printOptional(minBy(s.get()), "------------------Student MinBy Age---------------------\n",
				"------No Found Min Value ------------");
		printOptional(maxBy(s.get()), "------------------Student MaxBy Age---------------------\n",
				"------No Found Min Value ------------");
	}

	private static Optional<Student> minBy(Stream<Student> students) {
		return students.collect(Collectors.minBy(Comparator.comparing(Student::getAge)));
	}
	
	private static Optional<Student> maxBy(Stream<Student> students) {
		return students.collect(Collectors.maxBy(Comparator.comparing(Student::getAge)));
	}
}
