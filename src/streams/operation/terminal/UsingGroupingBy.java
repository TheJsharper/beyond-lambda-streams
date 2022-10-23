package streams.operation.terminal;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;
import static lambda.comparator.utils.Utils.print;
import static lambda.comparator.utils.Utils.printMapKeyInteger;

import java.util.Comparator;
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

		printGroupingByAge(getGroupingBySortedKeyValuePairClassificationAges(s.get()));

	}

	private static Map<String, List<Student>> getGroupingByGender(Stream<Student> students) {

		return students.collect(Collectors.groupingBy(Student::getGender));
	}

	private static Map<Integer, List<Student>> getGroupingByAges(Stream<Student> students) {

		return students.collect(Collectors.groupingBy(Student::getAge));
	}

	private static Map<String, List<Student>> getGroupingByClassificationAges(Stream<Student> students) {

		return students.collect(Collectors.groupingBy(keyMapperString()));
	}

	private static Map<String, List<Student>> getGroupingBySortedClassificationAges(Stream<Student> students) {

		return students.collect(
				Collectors.groupingBy(keyMapperString(), Collectors.collectingAndThen(Collectors.toList(), (list) -> {
					var orderedList = list.stream().sorted((Student s1, Student s2) -> s1.getAge() - s2.getAge())
							.collect(Collectors.toList());
					return orderedList;
				})));
	}

	private static Map<String, List<Student>> getGroupingBySortedKeyValueClassificationAges(Stream<Student> students) {

		var map = students.collect(
				Collectors.groupingBy(keyMapperString(), Collectors.collectingAndThen(Collectors.toList(), (list) -> {
					var orderedList = list.stream().sorted((Student s1, Student s2) -> s1.getAge() - s2.getAge())
							.collect(Collectors.toList());
					return orderedList;
				})));

		return new TreeMap<String, List<Student>>(map);
	}

	private static Map<KeyGrouping, List<Student>> getGroupingBySortedKeyValuePairClassificationAges(
			Stream<Student> students) {

		var result = students.collect(
				Collectors.groupingBy(keyGroupingMapper(), Collectors.collectingAndThen(Collectors.toList(), (list) ->

				list.stream().sorted(Comparator.comparingInt(Student::getAge)).collect(Collectors.toList()))));

		return result;

	}

	private static Function<Student, String> keyMapperString() {
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
				return "HIGHT_ADULT";

			} else if (s.getAge() >= 51 && s.getAge() <= 55) {
				return "OLD_ADULT";
			} else {
				return "OLD";
			}
		};
	}

	private static Function<Student, KeyGrouping> keyGroupingMapper() {
		return (Student s) -> {
			if (s.getAge() <= 5) {
				return new KeyGrouping(1, "CHILDHOOD", "Student.getAge() <= 5");
			} else if (s.getAge() >= 6 && s.getAge() <= 10) {
				return new KeyGrouping(2, "PRE_TEENAGER", "Student.getAge() >= 6 && Student.getAge() <= 10");
			} else if (s.getAge() >= 11 && s.getAge() <= 15) {
				return new KeyGrouping(3, "TEENAGER", "Student.getAge() >= 11 && Student.getAge() <= 15)");
			} else if (s.getAge() >= 16 && s.getAge() <= 20) {
				return new KeyGrouping(4, "POST_TEENAGER", "Student.getAge() >= 16 && Student.getAge() <= 20");

			} else if (s.getAge() >= 21 && s.getAge() <= 25) {
				return new KeyGrouping(5, "YOUNG_AGE", "Student.getAge() >= 21 && Student.getAge() <= 25");

			} else if (s.getAge() >= 26 && s.getAge() <= 30) {
				return new KeyGrouping(6, "POST_YOUNG", "Student.getAge() >= 26 && Student.getAge() <= 30");

			} else if (s.getAge() >= 31 && s.getAge() <= 35) {
				return new KeyGrouping(7, "JUVENILE_AGE", "Student.getAge() >= 31 && Student.getAge() <= 35");

			} else if (s.getAge() >= 36 && s.getAge() <= 40) {
				return new KeyGrouping(9, "ADULT", "Student.getAge() >= 36 && Student.getAge() <= 40");

			} else if (s.getAge() >= 41 && s.getAge() <= 45) {
				return new KeyGrouping(9, "MITTLE_ADULT", "Student.getAge() >= 41 && Student.getAge() <= 45");

			} else if (s.getAge() >= 46 && s.getAge() <= 50) {
				return new KeyGrouping(10, "HIGHT_ADULT", "Student.getAge() >= 46 && Student.getAge() <= 50");

			} else if (s.getAge() >= 51 && s.getAge() <= 55) {
				return new KeyGrouping(11, "OLD_ADULT", "Student.getAge() >= 51 && Student.getAge() <= 55");
			} else {
				return new KeyGrouping(12, "OLD", "Student.getAge() >=56");
			}
		};
	}

	private static void printGroupingByAge(Map<KeyGrouping, List<Student>> map) {
		map.forEach((KeyGrouping key, List<Student> values) -> {

			System.out.printf(
					"-----------------------------------Sorting index : %d Title : %s Grouping Logic: %s------------------------------------------\n",
					key.index(), key.title(), key.logicGrouping());
			print(values, "---------------Grouping student by age sorting ---------------------");
		});
	}

}

record KeyGrouping(Integer index, String title, String logicGrouping) implements Comparable<KeyGrouping> {

	@Override
	public int compareTo(KeyGrouping o) {

		return this.index - o.index;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null || obj.getClass() != this.getClass())
			return false;

		KeyGrouping keyGrouping = (KeyGrouping) obj;
		return (keyGrouping.index == this.index && keyGrouping.title.equals(this.title)
				&& keyGrouping.logicGrouping.equals(this.logicGrouping));
	}

	@Override
	public int hashCode() {
		return this.index;
	}

	@Override
	public String toString() {
		return "KeyGropping [index=" + index + ", title=" + title + "]";
	}

}
