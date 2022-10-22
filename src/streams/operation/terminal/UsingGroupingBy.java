package streams.operation.terminal;

import static lambda.comparator.utils.Utils.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class UsingGroupingBy {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		print(getGroupingByGender(s.get()), "getGroupingByGender");

		printMapKeyInteger(getGroupingByAges(s.get()), "getGroupingByAges");

		print(getGroupingByClassificationAges(s.get()), "getGroupingByClassificationAges");

		print(getGroupingBySortedClassificationAges(s.get()), "getGroupingBySortedClassificationAges");

		print(getGroupingBySortedKeyValueClassificationAges(s.get()), "getGroupingBySortedKeyValueClassificationAges");

	}

	private static Map<String, List<Student>> getGroupingByGender(Stream<Student> students) {

		return students.collect(Collectors.groupingBy(Student::getGender));
	}

	private static Map<Integer, List<Student>> getGroupingByAges(Stream<Student> students) {

		return students.collect(Collectors.groupingBy(Student::getAge));
	}

	private static Map<String, List<Student>> getGroupingByClassificationAges(Stream<Student> students) {

		return students.collect(Collectors.groupingBy(keyMapper()));
	}

	private static Map<String, List<Student>> getGroupingBySortedClassificationAges(Stream<Student> students) {

		return students.collect(
				Collectors.groupingBy(keyMapper(), Collectors.collectingAndThen(Collectors.toList(), (list) -> {
					var orderedList = list.stream().sorted((Student s1, Student s2) -> s1.getAge() - s2.getAge())
							.collect(Collectors.toList());
					return orderedList;
				})));
	}

	private static Map<String, List<Student>> getGroupingBySortedKeyValueClassificationAges(Stream<Student> students) {

		var map = students.collect(
				Collectors.groupingBy(keyMapper(), Collectors.collectingAndThen(Collectors.toList(), (list) -> {
					var orderedList = list.stream().sorted((Student s1, Student s2) -> s1.getAge() - s2.getAge())
							.collect(Collectors.toList());
					return orderedList;
				})));

		return new TreeMap<String, List<Student>>(map);
	}

	private static Function<Student, ? extends String> keyMapper() {
		return (Student s) -> {
			if (s.getAge() <= 5) {
				return "CHILDHOOD";
			} else if (s.getAge() >= 6 && s.getAge() <= 10) {
				return "PRE_TEENAGER";
			} else if (s.getAge() >= 11 && s.getAge() <= 15) {
				return "TEENAGER";
			} else if (s.getAge() >= 16 && s.getAge() <= 20) {
				return "POST_TEENAGER";

			} else if (s.getAge() >= 21 && s.getAge() <= 25) {
				return "YOUNG_AGE";

			} else if (s.getAge() >= 26 && s.getAge() <= 30) {
				return "POST_YOUNG";

			} else if (s.getAge() >= 31 && s.getAge() <= 35) {
				return "JUVENILE_AGE";

			} else if (s.getAge() >= 36 && s.getAge() <= 40) {
				return "ADULT";

			} else if (s.getAge() >= 41 && s.getAge() <= 45) {
				return "MITTLE_ADULT";

			} else if (s.getAge() >= 46 && s.getAge() <= 50) {
				return "MITTLE_ADULT";

			} else if (s.getAge() >= 51 && s.getAge() <= 55) {
				return "OLD_ADULT";
			} else {
				return "OLD";
			}
		};
	}

}
