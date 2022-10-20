package streams.factories;

import static lambda.comparator.utils.Utils.*;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class UsingFactoriesMethods {

	public static void main(String[] args) {
		var students = createStudentListWithAddresses();
		
		Stream<String> simpleStream = getSimpleStreamByOf("John", "Stephan", "Joe", "Jerry", "Tom");

		simpleStream.forEach(System.out::println);

		iterateSimpleLongRandom(1000, 10).forEach(System.out::println);

		iterateSimpleStringRandom(5).forEach(System.out::println);

		iterateOverStudents((Student s) -> s.getAge() <= 18).forEach(System.out::println);

		Supplier<Stream<Student>> supplier = () -> students.stream();

		var generatedList = generateStreamStudent(supplier).findFirst().get().collect(Collectors.toList());

		print(generatedList, "generatedList");

		var generateByBuilder = getStudentStreamFromBuilder(students).collect(Collectors.toList());

		print(generateByBuilder, "getStudentStreamFromBuilder");

	}

	private static Stream<String> getSimpleStreamByOf(String... names) {
		return Stream.of(names);
	}

	private static Stream<Long> iterateSimpleLongRandom(long seed, long limit) {
		var random = new Random();
		return Stream.iterate(seed, (Long cur) -> {
			var next = random.nextLong();
			return cur - next;
		}).limit(limit);
	}

	private static Stream<String> iterateSimpleStringRandom(long limit) {
		var random = new Random();
		var students = createStudentListWithAddresses();
		var lastNames = getAllLastName(students);
		return Stream.iterate(lastNames[0], (String cur) -> {
			var next = random.nextInt(0, lastNames.length);
			return cur + "," + lastNames[next];
		}).limit(limit);
	}

	private static Stream<Student> iterateOverStudents(Predicate<Student> predicate) {
		var students = createStudentListWithAddresses();
		var it = students.iterator();
		return Stream.iterate(students.get(0), predicate, (Student cur) -> it.next());
	}

	private static Stream<Stream<Student>> generateStreamStudent(Supplier<Stream<Student>> s) {
		return Stream.generate(s);
	}

	private static Stream<Student> getStudentStreamFromBuilder(List<Student> students) {
		Stream.Builder<Student> builder = Stream.builder();
		students.forEach((Student s) -> builder.accept(s));
		return builder.build();
	}

	private static String[] getAllLastName(List<Student> students) {
		return students.stream().map((Student s) -> s.getFirstName()).collect(Collectors.toList())
				.toArray(String[]::new);
	}

}
