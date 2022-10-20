package streams.operators;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.Student;

public class UsingAllAnyNoneMatch {

	public static void main(String[] args) {
		var students = createStudentListWithAddresses();

		printBasisFeatures(students);

		getAnyStudentByRangeAges(students, 18, 21)
				.ifPresentOrElse((List<Student> s) -> print(s, "getStudentByRangeAges")

						, () -> System.err.println("It does not exist Student between parameters args years"));

		getAllStudentByRangeAges(students, 18, 21)

				.ifPresentOrElse((List<Student> s) -> print(s, "getStudentByRangeAges")

						, () -> System.err.println("It does not exist Student between parameters args years"));

		getNoneAllStudentByRangeAges(students, 18, 21)

				.ifPresentOrElse((List<Student> s) -> print(s, "getStudentByRangeAges")

						, () -> System.err.println("It does not exist Student between parameters args years"));
	}

	private static Optional<List<Student>> getAnyStudentByRangeAges(List<Student> students, int fromAge, int toAge) {
		Predicate<Student> criteria = (Student s) -> s.getAge() >= fromAge && s.getAge() <= toAge;
		boolean isMatchStudentOfCriteria = students.stream().anyMatch(criteria);
		if (isMatchStudentOfCriteria) {
			return Optional.of(students.stream().filter(criteria)
					.sorted((Student s1, Student s2) -> s1.getAge() - s2.getAge()).collect(Collectors.toList()));
		} else
			return Optional.empty();
	}

	private static Optional<List<Student>> getAllStudentByRangeAges(List<Student> students, int fromAge, int toAge) {
		Predicate<Student> criteria = (Student s) -> s.getAge() >= fromAge && s.getAge() <= toAge;
		boolean isMatchStudentOfCriteria = students.stream().allMatch(criteria);
		if (isMatchStudentOfCriteria) {
			return Optional.of(students.stream().filter(criteria)
					.sorted((Student s1, Student s2) -> s1.getAge() - s2.getAge()).collect(Collectors.toList()));
		} else
			return Optional.empty();
	}

	private static Optional<List<Student>> getNoneAllStudentByRangeAges(List<Student> students, int fromAge,
			int toAge) {
		Predicate<Student> criteria = (Student s) -> s.getAge() >= fromAge && s.getAge() <= toAge;
		boolean isMatchStudentOfCriteria = students.stream().allMatch(criteria);
		if (isMatchStudentOfCriteria) {
			return Optional.of(students.stream().filter(criteria)
					.sorted((Student s1, Student s2) -> s1.getAge() - s2.getAge()).collect(Collectors.toList()));
		} else
			return Optional.empty();
	}

	private static boolean isAllStudent(List<Student> students) {
		return students.stream().allMatch((Student s) -> s.getAge() > 18);
	}

	private static boolean isAnyStudent(List<Student> students) {
		return students.stream().anyMatch((Student s) -> s.getAge() > 18);
	}

	private static boolean isNoneStudent(List<Student> students) {
		return students.stream().noneMatch((Student s) -> s.getAge() > 18);
	}

	private static void printBasisFeatures(List<Student> students) {
		System.out.println("Are There all students over 18 years old? :" + isAllStudent(students));
		System.out.println("Are There any students over 18 years old? :" + isAnyStudent(students));
		System.out.println("Aren't There all students over 18 years old? :" + isNoneStudent(students));

	}

	private static void print(List<Student> students, String label) {
		System.out.printf("----------------------- %s %s\n", label.toUpperCase(), "---------------------------------");
		students.forEach(UsingAllAnyNoneMatch::printStudent);

	}

	private static void printStudent(Student student) {
		System.out.println(student);
		if (student.getAddresses() != null) {
			System.out.println(student.getAddresses());
		}
	}

}
