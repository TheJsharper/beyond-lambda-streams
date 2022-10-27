package streams.operation;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class Main {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		System.out.println("->" + joiningWithoutParameter(s.get()));

		System.out.println("->" + joiningWithParameterDelimiter(s.get()));

		System.out.println("->" + joiningWithParameterDelPreAndSuffix(s.get()));

	}

	private static String joiningWithoutParameter(Stream<Student> students) {

		return students.map((Student s) -> s.getFirstName() + ", " + s.getLastName() + "\n").limit(3)
				.collect(Collectors.joining());

	}

	private static String joiningWithParameterDelimiter(Stream<Student> students) {

		CharSequence delimiter = ";";

		return students.map((Student s) -> s.getFirstName() + ", " + s.getLastName()).limit(3)
				.collect(Collectors.joining(delimiter));

	}

	private static String joiningWithParameterDelPreAndSuffix(Stream<Student> students) {

		CharSequence delimiter = ";";

		CharSequence prefix = "(";

		CharSequence suffix = ")";

		return students.map((Student s) -> s.getFirstName() + ", " + s.getLastName()).limit(3)
				.collect(Collectors.joining(delimiter, prefix, suffix));

	}

}
