package streams;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import static lambda.comparator.utils.Utils.*;

public class Main {

	public static void main(String[] args) {
		var students = getMergedSimpleStudentAdresses();
		printInitialStreamMap(students);
		printInitialFlatMap(students);
		printInitialStreamPeek(students);

	}

	private static void printInitialStreamMap(List<Student> students) {
		Predicate<Student> predicateStudent = (student -> student.getAge() >= 18);

		BiConsumer<String, List<Address>> consumer = (String key, List<Address> values) -> {
			System.out.println("Name :" + key);
			System.out.println("Addresses :" + values);
		};

		Map<String, List<Address>> mp = students.stream().filter(predicateStudent)
				.collect(Collectors.toMap(Student::getFirstName, Student::getAddresses));
		mp.forEach(consumer);

	}

	private static void printInitialFlatMap(List<Student> students) {
		Consumer<Address> consumer = (Address a) -> System.out.println(a);
		students.stream().map(Student::getAddresses).flatMap(List::stream).distinct().collect(Collectors.toList())
				.forEach(consumer);
	}

	private static void printInitialStreamPeek(List<Student> students) {
		Consumer<Student> consumerPeekStudent = (Student s) -> System.out.println(s);
		Consumer<String> consumerPeekString = (String s) -> System.out.println(s);
		students.stream().peek(consumerPeekStudent).map(Student::getLastName).peek(consumerPeekString).distinct()
				.collect(Collectors.toList()).forEach(consumerPeekString);
	}

}
