package fnc.buildin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingAdvancedFnc {

	private static BiFunction<List<Student>, Predicate<Student>, Map<String, String>> biFnc = (List<Student> students,
			Predicate<Student> predicate) -> {
		Map<String, String> studentMap = new HashMap<>();

		students.forEach((s) -> {
			if (predicate.test(s)) {
				studentMap.put(s.getFirstName() + " " + s.getLastName(), s.getGender());
			}
		});

		return studentMap;
	};

	private static Predicate<Student> predicateStudent = (Student s) -> {
		return s.getGender().equals("female");
	};

	public static void main(String[] args) {
		var students = Utils.getMergedSimpleStudentAdresses();
		Map<String, String> studentByGender = biFnc.apply(students, predicateStudent);
		studentByGender.forEach((k, v) -> {
			System.out.println("KEY: " + k + "  VALUE: " + v);
		});
	}

}
