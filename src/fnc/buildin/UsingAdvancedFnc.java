package fnc.buildin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingAdvancedFnc {

	private static BiFunction<List<Student>, Predicate<Student>, Map<String, String>> mapStudentByGeners = (
			List<Student> students, Predicate<Student> predicate) -> {
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
		Map<String, String> studentByGender = mapStudentByGeners.apply(students, predicateStudent);
		studentByGender.forEach((k, v) -> {
			System.out.println("KEY: " + k + "  VALUE: " + v);
		});
		getMapperByAddressCountry().apply(students, (Address a) -> {
			return a.getCountry().equals("USA");
		}).forEach((k, v) -> {
			System.out.print("Student: " + k);
			System.out.println(" Addresses:" + v);
		});

		System.out.println("------------------------------------Better with result Parameter-------------------------");
		ResultStudent result = new ResultStudent((Address a) -> a.getCountry().equals("MX"),
				new HashMap<String, List<Address>>());
		getAll().apply(students, result).forEach((k, v) -> {
			System.out.print("Student: " + k);
			System.out.println(" Addresses:" + v);
		});
	}

	private static BiFunction<List<Student>, Predicate<Address>, Map<String, List<Address>>> getMapperByAddressCountry() {

		Map<String, List<Address>> mappedStudents = new HashMap<>();

		return (List<Student> students, Predicate<Address> p) -> {
			students.forEach((s) -> {
				if (s.getAddresses().stream().filter(p).findFirst().isPresent()) {

					mappedStudents.put(s.getLastName() + ", " + s.getFirstName(), s.getAddresses());
				}
			});
			return mappedStudents;
		};
	}

	private static BiFunction<List<Student>, ResultStudent, Map<String, List<Address>>> getAll() {
		return (List<Student> students, ResultStudent resultStudent) -> {
			students.forEach((s) -> {
				if (s.getAddresses().stream().filter(resultStudent.predicate()).findFirst().isPresent()) {

					resultStudent.result().put(s.getLastName() + ", " + s.getFirstName(), s.getAddresses());
				}
			});
			return resultStudent.result();
		};
	}

}

record ResultStudent(Predicate<Address> predicate, Map<String, List<Address>> result) {

}