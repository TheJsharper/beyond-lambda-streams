package streams.operation;

import static lambda.comparator.utils.Utils.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class BasicsPartitionBy {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());
		partitioningWithOneParameters(s.get()).forEach((k, v) -> {
			System.out.println("====>" + k + "<========");
			print(v, "--------------------part by List of Student------------------");
		});
		paratitioningWithTwoParameters(s.get()).forEach((k, v) -> {
			System.out.println("====>" + k + "<========");
			v.forEach(System.out::println);

		});

	}

	private static Map<Boolean, List<Student>> partitioningWithOneParameters(Stream<Student> students) {

		Predicate<Student> condition = (Student s) -> s.getAge() >= 18;
		return students.collect(Collectors.partitioningBy(condition)).entrySet().stream().limit(10).collect(Collectors
				.toMap(Entry::getKey, (list) -> list.getValue().stream().limit(5).collect(Collectors.toList())));
	}

	private static Map<String, Set<Student>> paratitioningWithTwoParameters(Stream<Student> students) {

		Predicate<Student> condition = (Student s) -> s.getAge() >= 18;

		return students.collect(Collectors.partitioningBy(condition, Collectors.toSet())).entrySet().stream().limit(10)
				.collect(Collectors.toMap((Entry<Boolean, Set<Student>> e) -> e.getKey() ? "Adult" : "Minor",
						(Entry<Boolean, Set<Student>> e) -> e.getValue().stream().limit(5)
								.collect(Collectors.toSet())));

	}

}
