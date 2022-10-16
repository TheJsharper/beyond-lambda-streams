package fnc.buildin;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.DoubleConsumer;

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

	private static IntConsumer intConsumer = (int index) -> {
		System.out.println((index > 0 && index % 2 == 0));
	};
	private static LongConsumer longConsumer = (long index) -> {
		System.out.println((index > 0 && index % 2 == 0));
	};
	private static DoubleConsumer doubleConsumer = (double index) -> {
		System.out.println((index > 0 && index % 2 == 0));
	};
	private static ObjDoubleConsumer<Student> objectDoubleConsumer = (Student s, double value) -> {
		if (s.getAge() > value)
			System.out.println(s);
	};
	private static ObjIntConsumer<Student> objectIntegerConsumer = (Student s, int value) -> {
		if (s.getAge() > value)
			System.out.println(s);
	};
	private static ObjLongConsumer<Student> objectLongConsumer = (Student s, long value) -> {
		if (s.getAge() > value)
			System.out.println(s);
	};

	public static void main(String[] args) {
		System.out.print("IntConsumer() :");
		intConsumer.accept(12);

		System.out.print("LongConsumer() :");
		longConsumer.accept(7);

		System.out.print("DoubleConsumer() :");
		doubleConsumer.accept(14.0);

		printStudentAllProperties();

		printStudentPropertiesAndThenUpperCaseFirstName();

		printAllStudentPropertiesWithCondition();

		printObjectDoubleConsumer();

		printObjectIntConsumer();

		printObjectLongConsumer();
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

	private static void printObjectDoubleConsumer() {
		System.out.println(
				"-----------------------------Print ObjectDoubleConsumer--------------------------------------------------");
		var students = Utils.createStudentList();
		students.forEach((s) -> objectDoubleConsumer.accept(s, 52.5));
	}

	private static void printObjectIntConsumer() {
		System.out.println(
				"-----------------------------Print ObjectIntConsumer--------------------------------------------------");
		var students = Utils.createStudentList();
		students.forEach((s) -> objectIntegerConsumer.accept(s, 15));
	}

	private static void printObjectLongConsumer() {
		System.out.println(
				"-----------------------------Print ObjectLongConsumer--------------------------------------------------");
		var students = Utils.createStudentList();
		students.forEach((s) -> objectLongConsumer.accept(s, 15));
	}
}
