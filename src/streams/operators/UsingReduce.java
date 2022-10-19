package streams.operators;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lambda.comparator.lambda.model.Student;

public class UsingReduce {

	public static void main(String[] args) {
		var students = createStudentListWithAddresses();

		System.out.printf("Result :%d from getSimpleSum\n", getSimpleSum(Arrays.asList(new Integer[] { 1, 2, 3 })));

		var values = Arrays.asList(new Integer[] { 5, 7, 50, 4, 2, 1, -1, 100, 2 });

		getMaxValue(values).ifPresentOrElse((Integer value) -> System.out.println("Max Value " + value),
				() -> System.err.print("Not FOUND MAX Value"));

		getMinValue(values).ifPresentOrElse((Integer value) -> System.out.println("Min Value " + value),
				() -> System.err.print("Not FOUND Min Value"));

		print(getFullNames(students), "getFullNames");

		Optional<Student> youngest = getMaybeStudentYoungest(students);

		Optional<Student> oldest = getMaybeStudentOldest(students);

		youngest.ifPresentOrElse((Student student) -> System.out.println("Youngster Student " + student),
				() -> System.err.print("Student empty"));

		oldest.ifPresentOrElse((Student student) -> System.out.println("Oldest Student " + student),
				() -> System.err.print("Student empty"));

		System.out.println("The most older Student :" + getOldestStudent(students));

		System.out.println("The most younger Student: " + getYoungstStudent(students));

	}

	private static int getSimpleSum(List<Integer> values) {
		return values.stream().reduce(0, (Integer acc, Integer next) -> acc + next);
	}

	private static Optional<Integer> getMaxValue(List<Integer> values) {
		return values.stream().reduce((Integer acc, Integer next) -> acc > next ? acc : next);
	}

	private static Optional<Integer> getMinValue(List<Integer> values) {
		return values.stream().reduce((Integer acc, Integer next) -> acc > next ? next : acc);
	}

	private static Optional<Student> getMaybeStudentYoungest(List<Student> students) {
		return students.stream().reduce((Student a, Student b) -> a.getAge() >= b.getAge() ? b : a);

	}

	private static Optional<Student> getMaybeStudentOldest(List<Student> students) {
		return students.stream().reduce((Student a, Student b) -> a.getAge() >= b.getAge() ? a : b);

	}

	private static Student getOldestStudent(List<Student> students) {
		return students.stream().reduce(new Student(), (Student a, Student b) -> {
			if (a.getAge() == 0)
				return b;

			return a.getAge() >= b.getAge() ? a : b;
		});

	}

	private static Student getYoungstStudent(List<Student> students) {
		return students.stream().reduce(new Student(), (Student a, Student b) -> {
			if (a.getAge() == 0)
				return b;

			return a.getAge() >= b.getAge() ? b : a;
		});

	}

	private static List<String> getFullNames(List<Student> students) {
		return students.stream().reduce(new ArrayList<String>(), (List<String> fullNames, Student s) -> {
			fullNames.add(s.getFirstName() + ", " + s.getLastName());
			return fullNames;
		}, (a, b) -> a);
	}

	private static void print(List<String> students, String label) {
		System.out.printf("----------------------- %s %s\n", label, "---------------------------------");
		students.forEach(UsingReduce::printStudent);

	}

	private static void printStudent(String student) {
		System.out.println(student);

	}

}
