package streams.operation;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;
import static lambda.comparator.utils.Utils.print;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class BasicsMapping {
	public static void main(String[] args) {

		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		print(mapping(s.get()), "List of Student's name");

		print(mappingTheSame(s.get()), "List of Student's name");
	}

	private static List<String> mapping(Stream<Student> students) {
		return students.collect(Collectors.mapping(Student::getFirstName, Collectors.toList())).stream().limit(5)
				.collect(Collectors.toList());
	}

	private static List<String> mappingTheSame(Stream<Student> students) {
		return students.map(Student::getFirstName).collect(Collectors.toList()).stream().limit(5)
				.collect(Collectors.toList());

	}
}
