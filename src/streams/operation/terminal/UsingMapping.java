package streams.operation.terminal;

import static lambda.comparator.utils.Utils.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;

public class UsingMapping {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		getMapStudentAddresses(s.get()).forEach((k, v) -> System.out.println("==>" + k + v));

		print(getStudentsFirstNameByMappingList(s.get()),
				"--------------- getStudentsFirstNameByMappingList ------------------");

		print(getStudentsFirstNameByMappingSet(s.get()),
				"--------------- getStudentsFirstNameByMappingSet ------------------");

	}

	private static Set<String> getStudentsFirstNameByMappingSet(final Stream<Student> students) {
		return students.collect(Collectors.mapping(Student::getFirstName, Collectors.toSet()));

	}

	private static List<String> getStudentsFirstNameByMappingList(Stream<Student> students) {
		return students.collect(Collectors.mapping(Student::getFirstName, Collectors.toList()));

	}

	private static Map<String, List<Address>> getMapStudentAddresses(Stream<Student> students) {
		return students.collect(Collectors.mapping((Student s) -> s, Collectors
				.toMap((Student s) -> s.getFirstName() + " " + s.getLastName() + " ", Student::getAddresses)));

	}

}
