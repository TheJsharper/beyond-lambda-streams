package streams.operators;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class UsingDistinctCount {

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	public static void main(String[] args) {
		var students = createStudentListWithAddresses();

		Supplier<Stream<Student>> supplier = () -> students.stream();
		var merged = duplicateStudents(students);
		merged.sort((s1, s2) -> s1.getFirstName().compareTo(s2.getFirstName()));
		skipFirstFromAlp(students);
		// char[] arr = AB.toCharArray();
		// getCharacter().forEach((c)-> System.out.println(c));
		print(getDistinctStudents(merged), "getDistinctStudents");
		System.out.print(String.format("%s %d %s\n", "---------------- After Merging List", merged.stream().count(),
				"------------"));
		System.out.print(String.format("%s %d %s\n", "---------------- Before Merging List ", students.stream().count(),
				"------------"));

	}

	private static List<Student> getDistinctStudents(List<Student> students) {

		return students.stream().distinct().collect(Collectors.toList());
	}

	private static Map<String, Boolean> getCharacter() {

		return AB.chars().mapToObj(c -> (char) c)
				.collect(Collectors.toMap(String::valueOf, (Character c) -> Boolean.TRUE));
	}

	private static List<Student> skipFirstFromAlp(List<Student> students) {
		var stream = getCharacter().entrySet().stream();
		var list = getCharacter();
		var reversedValues = Arrays.asList(list.values().toArray(new Boolean[list.size()]));
		Collections.reverse(reversedValues);
		var revesedKeys = Arrays.asList(list.keySet().toArray(new String[list.size()]));
		Collections.reverse(revesedKeys);
		/*Stream<Entry<String, Boolean>> map = */reversedValues.stream().flatMap((Boolean v) -> {
			return revesedKeys.stream().collect(Collectors.toMap((s) -> s, (s) -> v)).entrySet().stream();

		}).forEach(System.out::println);

		students.stream().filter((Student s) -> {
			//var isMatch = stream.filter((ss) -> s.getFirstName().startsWith(ss.getKey())).count() > 0;
			return s.getFirstName().startsWith("A") || s.getFirstName().startsWith("M");
		}).forEach(System.out::println);
		;
		return null;
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
