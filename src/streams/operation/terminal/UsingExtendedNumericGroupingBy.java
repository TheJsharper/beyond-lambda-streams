package streams.operation.terminal;

import static lambda.comparator.utils.Utils.*;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;

public class UsingExtendedNumericGroupingBy {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		printGroupinByAgeAddresses(getGroupinByAgeAddresses(s.get()));

		getGroupinByAgeAddressesProcentage(s.get())
				.forEach((k, v) -> System.out.println("AGE :" + k + " Procentage :" + v));

	}

	private static Map<Integer, List<Address>> getGroupinByAgeAddresses(Stream<Student> students) {

		var result = students
				.collect(Collectors.groupingBy(Student::getAge, LinkedHashMap::new, Collectors.flatMapping(
						(s) -> s.getAddresses().stream(),
						Collectors.collectingAndThen(Collectors.toList(),
								(List<Address> list) -> list.stream().sorted(Comparator.comparing(Address::getCountry))
										.collect(Collectors.toList())))))
				.entrySet().stream().sorted(Comparator.comparing(Entry::getKey))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		return result;
	}

	private static Map<Integer, Double> getGroupinByAgeAddressesProcentage(Stream<Student> students) {

		var result = students
				.collect(Collectors.groupingBy(Student::getAge, LinkedHashMap::new,
						Collectors.flatMapping((s) -> s.getAddresses().stream(),
								Collectors.collectingAndThen(Collectors.toList(),
										(List<Address> list) -> list.size()))))
				.entrySet().stream().sorted(Comparator.comparing(Entry::getKey))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		Map<Integer, Double> procentage = result.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, (e) -> (double) e.getValue() / result.size()));

		return procentage;
	}

	private static void printGroupinByAgeAddresses(Map<Integer, List<Address>> map) {
		System.out.println(
				"================ START Print Grouping By Students Ages  List of Addresses=============================");
		map.forEach((k, v) -> {
			System.out.println("Age: " + k);
			printAddresses(v, "--------------List of Addresses  -------------");
		});
		System.out.println(
				"================ END Print Grouping By Students Ages  List of Addresses=============================");

	}
}
