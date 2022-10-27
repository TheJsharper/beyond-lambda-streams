package streams.operation;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;
import static lambda.comparator.utils.Utils.print;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class BasicsGroupingBy {

	public static void main(String[] args) {

		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		print(groupingByOneParameterByGender(s.get()), "--------------------Map by Gender---------------------");

		print(groupingByOneParameterByAge(s.get()),
				"--------------------Map by Adult or Minor Ages---------------------");

		groupingByTwoParameterByAgesAndGender(s.get()).forEach((k, v) -> {
			System.out.println("First Level Mapping Key: " + k);
			print(v, "-------------------All Student Gender------------");
		});

		System.out.println("-------------------THREE ARGUMENTS--------------------");

		groupingByThreeParameters(s.get()).forEach((k, v) -> {
			System.out.println("First Level Mapping Key: " + k);
			print(v, "-------------------All Student Gender------------");
		});
	}

	private static Map<String, List<Student>> groupingByOneParameterByGender(Stream<Student> students) {

		Function<Student, String> classifier = (Student s) -> s.getGender();

		Function<Entry<String, List<Student>>, String> mapperKeys = (Entry<String, List<Student>> e) -> e.getKey();

		Function<Entry<String, List<Student>>, List<Student>> mapperValues = (Entry<String, List<Student>> e) -> e
				.getValue().stream().limit(5).collect(Collectors.toList());

		return students.collect(Collectors.groupingBy(classifier)).entrySet().stream().limit(5)
				.collect(Collectors.toMap(mapperKeys, mapperValues));

	}

	private static Map<String, List<Student>> groupingByOneParameterByAge(Stream<Student> students) {

		Function<Student, String> classifier = (Student s) -> s.getAge() >= 18 ? "Adult" : "Minor";

		Function<Entry<String, List<Student>>, String> mapperKeys = (Entry<String, List<Student>> e) -> e.getKey();

		Function<Entry<String, List<Student>>, List<Student>> mapperValues = (Entry<String, List<Student>> e) -> e
				.getValue().stream().limit(5).collect(Collectors.toList());

		return students.collect(Collectors.groupingBy(classifier)).entrySet().stream().limit(5)
				.collect(Collectors.toMap(mapperKeys, mapperValues));

	}

	private static Map<String, Map<String, List<Student>>> groupingByTwoParameterByAgesAndGender(
			Stream<Student> students) {

		Function<Student, String> classifier = (Student s) -> s.getAge() >= 18 ? "Adult" : "Minor";

		Collector<Student, ?, Map<String, List<Student>>> downstream = Collectors
				.groupingBy((Student s) -> s.getGender());

		return students.collect(Collectors.groupingBy(classifier, downstream)).entrySet().stream().collect(
				Collectors.toMap(Entry::getKey, (e) -> e.getValue().entrySet().stream().limit(5).collect(Collectors
						.toMap(Entry::getKey, (ec) -> ec.getValue().stream().limit(5).collect(Collectors.toList())))));

	}

	public static LinkedHashMap<String, Map<String, List<Student>>> groupingByThreeParameters(
			Stream<Student> students) {

		Function<Student, String> classifier = (Student s) -> s.getAge() >= 18 ? "Adult" : "Minor";

		Collector<Student, ?, Map<String, List<Student>>> downstream = Collectors
				.groupingBy((Student s) -> s.getGender());

		Supplier<LinkedHashMap<String, Map<String, List<Student>>>> mapFactory = () -> new LinkedHashMap<String, Map<String, List<Student>>>();

		var intermedia = students.collect(Collectors.groupingBy(classifier, mapFactory, downstream));

		LinkedHashMap<String, Map<String, List<Student>>> result = limit(intermedia, 5, mapFactory);

		return result;
	}

	private static LinkedHashMap<String, Map<String, List<Student>>> limit(
			LinkedHashMap<String, Map<String, List<Student>>> students, int limit,
			Supplier<LinkedHashMap<String, Map<String, List<Student>>>> mapFactory) {

		return students.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, (e) -> e.getValue().entrySet().stream().limit(limit)
						.collect(Collectors.toMap((ee) -> e.getKey(),
								(ec) -> ec.getValue().stream().limit(limit).collect(Collectors.toList()), (a, b) -> b)),
						(a, b) -> {
							return a.entrySet().stream().collect(Collectors.flatMapping((aa) -> b.entrySet().stream(),
									Collectors.toMap(Entry::getKey, Entry::getValue)));

						}, mapFactory));

	}
}
