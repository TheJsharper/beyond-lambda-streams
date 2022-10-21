package streams.operation.terminal;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class UsingJoining {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		System.out.println("--->Joining Default:" + getJoiningFirstNameDefault(s.get()));

		System.out.println();

		System.out.println("--->Joining -:" + getJoiningFirstNameDelimiter(s.get()));

		System.out.println();

		System.out.println("--->Joining  with prefixes:" + getJoiningWithDelimiterWithPrefix(s.get()));
	}

	private static String getJoiningFirstNameDefault(Stream<Student> students) {
		return students.map(Student::getFirstName).limit(100).collect(Collectors.joining());
	}

	private static String getJoiningFirstNameDelimiter(Stream<Student> students) {
		return students.map(Student::getFirstName).limit(100).collect(Collectors.joining("-"));
	}

	private static String getJoiningWithDelimiterWithPrefix(Stream<Student> students) {
		return students.map(Student::getLastName).limit(100).collect(Collectors.joining("-", "[", "]"));
	}

}
