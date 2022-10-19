package streams.operators;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.Student;

public class UsingAdvancedSkipLimit {

	public static void main(String[] args) {
		var students = createStudentListWithAddresses();

		print(getFirstNameSortedDescendingListTopNr(students, 1), "getFirstNameSortedDescendingListTopNr");

		print(getLastNameSortedAscendingListTopNr(students, 1), "getLastNameSortedAscendingListTopNr");

		print(getLastNameSortedAscendingListPaging(students, 1, 3), "getLastNameSortedAscendingListPaging");

		print(getFirstNameSortedDescendingListPaging(students, 1, 3), "getLastNameSortedDescendingListPaging");

		print(getGroupByAgesSortedDescendingListPaging(students, 3, 7), "Values",
				"getGroupByAgesSortedDescendingListPaging");

	}

	private static List<Student> getFirstNameSortedDescendingListTopNr(List<Student> students, long top) {
		return top >= 1
				? students.stream().sorted((Student s1, Student s2) -> s2.getFirstName().compareTo(s1.getFirstName()))
						.limit(top).collect(Collectors.toList())
				: new ArrayList<Student>();
	}

	private static List<Student> getLastNameSortedAscendingListTopNr(List<Student> students, long top) {
		return top >= 1
				? students.stream().sorted((Student s1, Student s2) -> s1.getLastName().compareTo(s2.getLastName()))
						.limit(top).collect(Collectors.toList())
				: new ArrayList<Student>();
	}

	private static List<Student> getLastNameSortedAscendingListPaging(List<Student> students, long from, long to) {
		return to >= 1 && from <= to && from >= 1
				? students.stream().sorted((Student s1, Student s2) -> s1.getLastName().compareTo(s2.getLastName()))
						.skip(from).limit(to).collect(Collectors.toList())
				: new ArrayList<Student>();
	}

	private static List<Student> getFirstNameSortedDescendingListPaging(List<Student> students, long from, long to) {
		return to >= 1 && from <= to && from >= 1
				? students.stream().sorted((Student s1, Student s2) -> s2.getFirstName().compareTo(s1.getFirstName()))
						.skip(from).limit(to).collect(Collectors.toList())
				: new ArrayList<Student>();
	}

	private static Map<String, List<Student>> getGroupByAgesSortedDescendingListPaging(List<Student> students,
			long from, long to) {

		if (to >= 1 && from <= to && from >= 1) {

			return students.stream().sorted((Student s1, Student s2) -> s2.getFirstName().compareTo(s1.getFirstName()))
					.reduce(new HashMap<String, List<Student>>(), UsingAdvancedSkipLimit::reducer, (a, b) -> a)
					.entrySet().stream().skip(from).limit(to)
					.collect(Collectors.toMap((entry) -> entry.getKey(), (entry) -> entry.getValue()));
		} else
			return new HashMap<String, List<Student>>();
	}

	private static HashMap<String, List<Student>> reducer(HashMap<String, List<Student>> acc, Student s) {
		if (!acc.keySet().contains(String.valueOf(s.getAge()))) {
			var values = new ArrayList<Student>();
			values.add(s);
			acc.put(String.valueOf(s.getAge()), values);
		} else
			acc.get(String.valueOf(s.getAge())).add(s);

		return acc;
	}

	private static void print(Map<String, List<Student>> students, String label, String key) {
		System.out.printf("----------------------- %s %s\n", key.toUpperCase(), "---------------------------------");

		students.forEach((k, s) -> {
			System.out.println("Group by: " + k);
			print(s, label);
		});
	}

	private static void print(List<Student> students, String label) {
		System.out.printf("----------------------- %s %s\n", label.toUpperCase(), "---------------------------------");
		students.forEach(UsingAdvancedSkipLimit::printStudent);

	}

	private static void printStudent(Student student) {
		System.out.println(student);
		if (student.getAddresses() != null) {
			System.out.println(student.getAddresses());
		}
	}
}
