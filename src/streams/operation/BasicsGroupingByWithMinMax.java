package streams.operation;

import static lambda.comparator.utils.Utils.*;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class BasicsGroupingByWithMinMax {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		groupingByWithMax(s.get()).forEach((k, v) -> {
			System.out.println("Gender : " + k);
			printOptional(v, "-----------Student by Max Age----------\n", "No Found");
		});
		System.out.println();
		groupingByWithMin(s.get()).forEach((k, v) -> {
			System.out.println("=======================" + k + "==============================");
			printStudent(v);
		});
	}

	private static Map<String, Optional<Student>> groupingByWithMax(Stream<Student> students) {
		Function<Student, String> classifier = (Student s) -> s.getGender();

		return students
				.collect(Collectors.groupingBy(classifier, Collectors.maxBy(Comparator.comparing(Student::getAge))));
	}

	private static Map<String, Student> groupingByWithMin(Stream<Student> students) {
		Function<Student, String> classifier = (Student s) -> s.getGender();

		Collector<Student, ?, Optional<Student>> minBy = Collectors.minBy(Comparator.comparing(Student::getAge));

		Collector<Student, ?, Student> collectingAndThen = Collectors.collectingAndThen(minBy,
				(Optional<Student> s) -> s.isPresent() ? s.get() : new Student());

		return students.collect(Collectors.groupingBy(classifier, collectingAndThen));
	}

}
