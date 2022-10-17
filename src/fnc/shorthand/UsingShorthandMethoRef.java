package fnc.shorthand;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingShorthandMethoRef {

	public static Predicate<Student> predicateUsingLambda = (Student s) -> s.getAge() > 18;

	public static Predicate<Student> predicateUsingMethRef = UsingShorthandMethoRef::greatherThan;

	public static BiPredicate<Student, Integer> bipredicateUsingMethRef = UsingShorthandMethoRef::greatherThan;

	public static boolean greatherThan(Student s) {
		return s.getAge() > 18;
	}

	public static boolean greatherThan(Student s, Integer age) {
		return s.getAge() > 18;
	}

	public static void main(String[] args) {
		var students = Utils.getSimpleStudents();

		System.out.println("----------------------------Method referenece style----------------------------------");
		students.forEach(UsingShorthandMethoRef::printer);
		System.out.println("----------------------------Lambda style----------------------------------");
		students.forEach(UsingShorthandMethoRef::printerLambda);

	}

	private static void printer(Student s) {
		if (predicateUsingMethRef.test(s))
			System.out.println(s);
	}

	private static void printerLambda(Student s) {
		if (predicateUsingLambda.test(s))
			System.out.println(s);
	}

}
