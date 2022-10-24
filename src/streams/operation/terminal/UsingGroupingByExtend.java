package streams.operation.terminal;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;
import static lambda.comparator.utils.Utils.print;
import static lambda.comparator.utils.Utils.printAddresses;
import static lambda.comparator.utils.Utils.printMapAddresses;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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

		printGroupingByStudentGenderAndAddress(getGroupingStudentAgesMapCountryAddresses(s.get()));
	}

	private static Map<String, Map<String, List<Student>>> getGroupingStudentAges(Stream<Student> students) {
		return students.collect(Collectors.groupingBy(Utils.keyMapperByAgesGroupingString(),
				Collectors.groupingBy((s) -> s.getGender(),
						Collectors.collectingAndThen(Collectors.toList(), (list) -> list.stream()
								.sorted(Comparator.comparingInt(Student::getAge)).collect(Collectors.toList())))));

	}

	private static Map<String, Map<String, List<Address>>> getGroupingStudentAgesMapCountryAddresses(
			Stream<Student> students) {

		var result = students.collect(Collectors.groupingBy(Utils.keyMapperByAgesGroupingString(),

				Collectors.flatMapping((s) -> s.getAddresses().stream(), Collectors.collectingAndThen(
						Collectors.groupingBy((Address a) -> a.getCountry()), (Map<String, List<Address>> list) -> {
							var sorted = list.entrySet().stream().sorted(getComparatorMap())
									.collect(Collectors.toList());
							return sorted.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue));
						}))

		));

		return result;

	}

	private static Comparator<Map.Entry<String, List<Address>>> getComparatorMap() {

		return (Entry<String, List<Address>> list1, Entry<String, List<Address>> list2) -> {
			var sorted1 = list1.getValue().stream().sorted(Comparator.comparing(Address::getCity))
					.collect(Collectors.toList());
			var sorted2 = list2.getValue().stream().sorted(Comparator.comparing(Address::getCity))
					.collect(Collectors.toList());
			list1.setValue(sorted1);
			list2.setValue(sorted2);
			return list1.getValue().size() - list2.getValue().size();
		};
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

	private static void printGroupingByStudentGenderAndAddress(Map<String, Map<String, List<Address>>> students) {

		BiConsumer<String, Map<String, List<Address>>> printer = (String key, Map<String, List<Address>> values) -> {
			System.out.println("KEY========" + key + "===================");
			printMapAddresses(values, "-------------------Addresses---------------------");

		};
		System.out.println(
				"================ START Print Grouping By Students Ages and Map By Country and List of Addresses=============================");
		students.forEach(printer);
		System.out.println(
				"================ END Print Grouping By Students Ages and Map By Country and List of Addresses=============================");
	}

	private static void printGroupingStudentsAges(Map<String, Map<String, List<Student>>> students) {
		System.out.println("================ Start Print Grouping Students Ages=============================");
		BiConsumer<String, Map<String, List<Student>>> printer = (String k, Map<String, List<Student>> v) -> {
			System.out.println("KEY========" + k + "===================");
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
