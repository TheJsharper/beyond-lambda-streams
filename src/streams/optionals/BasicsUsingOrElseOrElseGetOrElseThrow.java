package streams.optionals;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import lambda.comparator.lambda.model.Student;

public class BasicsUsingOrElseOrElseGetOrElseThrow {

	public static void main(String[] args) {

		usingOrElse();

		usingOrElseGet();

		usingOrElseThrow();
	}

	private static void usingOrElse() {

		System.out.println("getValueOrElse :" + getValueOrElse());

		System.err.println("getMaybeOrElse :" + getMaybeOrElse());
	}

	private static void usingOrElseGet() {
		System.out.println("getValueOrElseGet :" + getValueOrElseGet());

		System.err.println("getMaybeOrElseGet :" + getMaybeOrElseGet());
	}

	private static void usingOrElseThrow() {

		System.out.println("getValueOrElseThrow :" + getValueOrElseThrow());
		try {

			System.err.println("getMaybeOrElseGet :" + getMaybeOrElseThrow());

		} catch (NoSuchElementException e) {

			System.err.println("Exception is been thrown :" + e.getMessage());
		}

	}

	private static String getValueOrElse() {
		Optional<Student> optionalStudent = Optional
				.ofNullable(new Student("Max", "Bauer", 20, "max.bauer@domain.com", "male", null));

		String firstName = optionalStudent.map(Student::getFirstName).orElse("NoFirstName");

		return firstName;
	}

	private static String getMaybeOrElse() {
		Optional<Student> optionalStudent = Optional.ofNullable(new Student());

		String firstName = optionalStudent.map(Student::getFirstName).orElse("NoFirstName");

		return firstName;
	}

	private static String getValueOrElseGet() {
		Optional<Student> optionalStudent = Optional
				.ofNullable(new Student("Max", "Bauer", 20, "max.bauer@domain.com", "male", null));

		Supplier<String> defaultValue = () -> "DefaultFirstName";

		String firstName = optionalStudent.map(Student::getFirstName).orElseGet(defaultValue);

		return firstName;
	}

	private static String getMaybeOrElseGet() {
		Optional<Student> optionalStudent = Optional.ofNullable(new Student());

		String firstName = optionalStudent.map(Student::getFirstName).orElseGet(() -> "DefaultFirstName");

		return firstName;
	}

	private static String getValueOrElseThrow() {
		Optional<Student> optionalStudent = Optional
				.ofNullable(new Student("Max", "Bauer", 20, "max.bauer@domain.com", "male", null));

		Supplier<NoSuchElementException> defaultValueException = () -> new NoSuchElementException("DefaultFirstName");

		String firstName = optionalStudent.map(Student::getFirstName).orElseThrow(defaultValueException);

		return firstName;
	}

	private static String getMaybeOrElseThrow() {
		Optional<Student> optionalStudent = Optional.ofNullable(new Student());

		String firstName = optionalStudent.map(Student::getFirstName)
				.orElseThrow(() -> new NoSuchElementException("DefaultFirstName"));

		return firstName;
	}

}
