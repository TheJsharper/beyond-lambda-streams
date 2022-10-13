package fnc.buildin;

import static lambda.comparator.utils.Utils.getMergedSimpleStudentAdresses;
import static lambda.comparator.utils.Utils.getSimpleAddresses;
import static lambda.comparator.utils.Utils.getSimpleStudents;

import java.util.List;
import java.util.function.BiConsumer;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;

public class UsingBiConsumer {

	private static BiConsumer<String, String> justSimpleBiConsumer = (String arg01, String arg02) -> System.out
			.println("Args01: " + arg01 + " Args02: " + arg02);

	public static void main(String[] args) {
		justSimpleBiConsumer.accept("Hello ", "Func");
		printMultiplication().accept(10, 10);
		printAddtion().accept(10, 10);
		printDivision().accept(10, 2);
		printMultiplication().andThen(printDivision()).andThen(printAddtion()).accept(100, 10);
		printListAndSubListChild();
		printListStudentNameAndAddressStreet();
	}

	private static BiConsumer<Integer, Integer> printMultiplication() {

		return (Integer a, Integer b) -> System.out.println("A * B : " + (a * b));
	}

	private static BiConsumer<Integer, Integer> printAddtion() {

		return (Integer a, Integer b) -> System.out.println("A + B : " + (a + b));
	}

	private static BiConsumer<Integer, Integer> printDivision() {

		return (Integer a, Integer b) -> System.out.println("A / B : " + (a / b));
	}

	private static void printListAndSubListChild() {
		BiConsumer<List<Student>, List<Address>> printterNode = (List<Student> students, List<Address> addresses) -> {
			System.out.println("----------------- STUDENTS INPUT------------------------");
			students.forEach((s) -> System.out.println("Students " + s));
			System.out.println("----------------- ADDRESES INPUT------------------------");
			addresses.forEach((a) -> System.out.println("Address" + a));
		};
		var students = getSimpleStudents();
		var addresses = getSimpleAddresses();
		printterNode.accept(students, addresses);
	}

	private static void printListStudentNameAndAddressStreet() {
		BiConsumer<String, List<Address>> studentNameAddressesPrintter = (String name, List<Address> streets) -> {
			System.out.println("Name: " + name + " Streets: " + streets);

		};
		List<Student> students = getMergedSimpleStudentAdresses();
		students.forEach((s) -> studentNameAddressesPrintter.accept(s.getFirstName(), s.getAddresses()));
	}

}
