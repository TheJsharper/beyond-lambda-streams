package fnc.shorthand;

import java.util.function.Function;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingFncAsMethodRef {

	/**
	 * Class::intancemethod
	 */
	private static Function<String, String> upperCaseFncLambda = (s) -> s.toUpperCase();
	private static Function<String, String> upperCaseFncMethodRef = String::toUpperCase;

	public static void main(String[] args) {
		System.out.println(upperCaseFncLambda.apply("UpperCaseFncLambda"));
		System.out.println(upperCaseFncMethodRef.apply("UpperCaseFncLambda"));

		var students = Utils.createStudentList();
		students.forEach(UsingFncAsMethodRef::printer);
	}

	/**
	 * Consumer as owner  static method
	 * @param s Student
	 */
	private static void printer(Student s) {
		System.out.println(s);
	}

}
