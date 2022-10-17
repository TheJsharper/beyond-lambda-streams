package streams.operators;

import static lambda.comparator.utils.Utils.getSimpleStudents;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.Student;

public class UsingMap {

	public static void main(String[] args) {
		var students = getSimpleStudents();
		print(getFirstNameUpperCase(students), "getFirstNameUpperCase");
		print(getSetOfLastNameUpperCase(students), "getSetOfLastNameUpperCase");
		print(getFirstNameLengthMap(students), "getFirstNameLengthMap");

	}

	private static List<String> getFirstNameUpperCase(List<Student> students) {
		return students.stream().map(Student::getFirstName).map(String::toUpperCase).collect(Collectors.toList());
	}

	private static Set<String> getSetOfLastNameUpperCase(List<Student> students) {
		return students.stream().map(Student::getLastName).map(String::toUpperCase).collect(Collectors.toSet());
	}

	private static Map<String, Integer> getFirstNameLengthMap(List<Student> students) {
		return students.stream().map(Student::getFirstName).collect(Collectors.toMap(String::toString, String::length));
	}

	private static void print(Collection<String> names, String Label) {
		System.out.println("-------------------------" + Label.toUpperCase() + "----------------------");
		Consumer<String> consumer = (name) -> System.out.println(name);
		names.forEach(consumer);
	}

	private static void print(Map<String, Integer> map, String Label) {
		System.out.println("-------------------------" + Label.toUpperCase() + "----------------------");
		BiConsumer<String, Integer> consumer = (name, lenght) -> System.out.println(name + " " + lenght);
		map.forEach(consumer);
	}

}
