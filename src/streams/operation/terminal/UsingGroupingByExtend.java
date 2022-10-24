package streams.operation.terminal;

import static lambda.comparator.utils.Utils.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingGroupingByExtend {
	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		printGroupingStudentsAges(getGroupingStudentAges(s.get()));

		printGroupingByStudentGenderListOfAddress(getGroupingByStudentGenderAndAddress(s.get()));
	}

	private static Map<String, Map<String, List<Student>>> getGroupingStudentAges(Stream<Student> students) {
		return students.collect(Collectors.groupingBy(Utils.keyMapperString(),
				Collectors.groupingBy((s) -> s.getGender(),
						Collectors.collectingAndThen(Collectors.toList(), (list) -> list.stream()
								.sorted(Comparator.comparingInt(Student::getAge)).collect(Collectors.toList())))));

	}

	private static Map<String, List<Address>> getGroupingByStudentGenderAndAddress(Stream<Student> students) {


		return students.collect(
				Collectors.groupingBy(Student::getGender, Collectors.flatMapping((s) -> s.getAddresses().stream(),
						Collectors.collectingAndThen(Collectors.toList(), (List<Address> list) -> {
							var sortedCountries = list.stream().sorted(Comparator.comparing(Address::getCountry))
									.collect(Collectors.toList());

							return sortedCountries;
						}))));

	}

	private static void printGroupingStudentsAges(Map<String, Map<String, List<Student>>> students) {
		System.out.println("================ Start Print Grouping Students Ages=============================");
		BiConsumer<String, Map<String, List<Student>>> printer = (String k, Map<String, List<Student>> v) -> {
			System.out.println(k);
			print(v, "---------------Values---------------------");
		};
		students.forEach(printer);
		System.out.println("================ END Print Grouping Students Ages=============================");
	}

	private static void printGroupingByStudentGenderListOfAddress(Map<String, List<Address>> students) {
		BiConsumer<String, List<Address>> printer = (String key, List<Address> values) -> {
			System.out.printf("--------------Student Gender  %s -------------------------------\n", key);
			printAddresses(values, "-----------Values Addresses------------------");
		};
		System.out.println(
				"================ Start Print GroupingBy Students Gender to List of Addresses=============================");
		students.forEach(printer);
		System.out.println(
				"================ End Print GroupingBy Students Gender to List of Addresses=============================");

	}
}
