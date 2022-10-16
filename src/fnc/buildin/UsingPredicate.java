package fnc.buildin;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import static lambda.comparator.utils.Utils.*;

public class UsingPredicate {

	private static Predicate<Student> isUpper18YearOld = (Student s) -> s.getAge() >= 18;

	private static Predicate<Student> isFemale = (Student s) -> s.getGender() == "female";

	private static BiConsumer<String, List<Address>> printterByNameAndAddresses = (String name,
			List<Address> address) -> System.out.println("Name: " + name + " Address:" + address);

	private static Consumer<Student> printterByUpper18YearOld = (Student s) -> {
		if (isUpper18YearOld.test(s)) {
			printterByNameAndAddresses.accept(s.getFirstName() + " " + s.getLastName(), s.getAddresses());
		}
	};
	private static Consumer<Student> printterByFemaleStudents = (Student s) -> {
		if (isFemale.test(s)) {
			printterByNameAndAddresses.accept(s.getFirstName() + " " + s.getLastName(), s.getAddresses());
		}
	};
	private static Consumer<Student> printterByFemaleAndUpper18YearStudents = (Student s) -> {
		if (isFemale.and(isUpper18YearOld).test(s)) {
			printterByNameAndAddresses.accept(s.getFirstName() + " " + s.getLastName(), s.getAddresses());
		}
	};

	private static IntPredicate isModule10 = (int value) -> value % 10 == 0;

	private static DoublePredicate isValuePiByPlace3 = (double value) -> value == 3.142;

	private static LongPredicate isMaxLong = (long value) -> value == Long.MAX_VALUE;

	public static void main(String[] args) {

		printBasicsPredicate();
		var students = getMergedSimpleStudentAdresses();

		System.out.println("-------------------- Print Student by Female and 18 years-----------------");
		students.forEach(printterByFemaleAndUpper18YearStudents);

		System.out.println("-------------------- Print Student by Female-----------------");
		students.forEach(printterByFemaleStudents);

		System.out.println("-------------------- Print Studentby 18 years-----------------");
		students.forEach(printterByUpper18YearOld);

		System.out.println("Is Mdoule 10 == 30 :" + isModule10.test(50));

		System.out.println("Is PI 3.14 :" + isValuePiByPlace3.test(3.14));

		System.out.println("Is Long.MIN_VALUE ==  Long.MAX_VALUE :" + isMaxLong.test(Long.MIN_VALUE));
	}

	private static void printBasicsPredicate() {
		Predicate<Integer> isEven = (Integer i) -> {
			return i % 2 == 0;
		};
		Predicate<Integer> isEvenShortHand = (Integer i) -> i % 2 == 0;
		Predicate<Integer> isFiveModule = (Integer i) -> i % 5 == 0;

		System.out.println("Is 10 even : " + isEven.test(10));
		System.out.println("Is 12 even : " + isEvenShortHand.and(isEven).test(12));
		System.out.println("Is 15 even : " + isEvenShortHand.and(isEven).test(15));
		System.out.println("Is 15 even Or 15 module 5 : " + isEvenShortHand.or(isFiveModule).test(15));
		System.out.println("Is 15 module 5 Or 15 even : " + isFiveModule.or(isEvenShortHand).test(15));
		System.out.println("Is 15 even and 15 module 5 : " + isEvenShortHand.and(isFiveModule).test(15));
		System.out.println("Is 15 module 5 and  5 even  : " + isFiveModule.and(isEvenShortHand).test(15));
		System.out.println("Is 15 is NOT module 5  : " + isFiveModule.negate().test(15));
		System.out.println("Is NOT 2 is even   : " + isFiveModule.negate().test(2));
	}
}
