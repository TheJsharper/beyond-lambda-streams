package fnc.buildin;

import java.util.function.Consumer;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingConsumer {

	private static Consumer<Student> c1 = (Student s) -> System.out.println(s);
	private static Consumer<Student> c2 = (Student s) -> System.out
			.println("FirstName: " + s.getFirstName().toUpperCase() + "IP address: " + s.getIpAddress());
	private static Consumer<Student> c3 = (Student s) -> System.out
			.println("FirstName :" + s.getFirstName().toUpperCase());
	private static Consumer<Student> c4 = (Student s) -> System.out
			.println("LastName: " + s.getLastName() + " Age: " + s.getAge());

	public static void main(String[] args) {
		printStudentAllProperties();
		printStudentPropertiesAndThenUpperCaseFirstName();

		printAllStudentPropertiesWithCondition();
	}

	private static void printStudentAllProperties() {

		System.out.println(
				"-----------------------------Print Students properties--------------------------------------------------");

		var students = Utils.createStudentList();
		students.forEach(c1.andThen(c2));

	}

	private static void printStudentPropertiesAndThenUpperCaseFirstName() {

		System.out.println(
				"-----------------------------Print ALL Students properties and then UpperCase First Name--------------------------------------------------");

		var students = Utils.createStudentList();
		students.forEach(c1.andThen(c3));

	}

	private static void printAllStudentPropertiesWithCondition() {
		var students = Utils.createStudentList();
		students.forEach((s) -> {
			if (s.getAge() >= 20 && s.getAge() <= 50) {
				c3.andThen(c4).accept(s);
			}
		});
	}
}
