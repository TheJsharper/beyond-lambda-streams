package fnc.buildin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

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

	@SuppressWarnings("serial")
	private static List<Student> getSimpleStudents() {

		var students = new ArrayList<Student>() {

			{
				add(new Student("Max", "Bauer", 20, "max.bauer@domain.com", "male", null));
				add(new Student("Min", "Schmitz", 24, "min.schmitz@domain.com", "male", null));
				add(new Student("Linda", "Berger", 22, "linda.berger@domain.com", "female", null));
				add(new Student("Rosa", "Maier", 15, "rosa.maier@maydomain.com", "female", null));
			}
		};
		return students;
	}

	@SuppressWarnings("serial")
	private static List<Address> getSimpleAddresses() {
		return new ArrayList<Address>() {
			{
				add(new Address(1, "Mainstreet,56", "New York", "7852NY8588", "USA"));
				add(new Address(2, "Mainstreet,85", "Los Angeles", "3692LA989", "USA"));
				add(new Address(3, "Mainstreet,78", "Chicago", "7852CH8588", "USA"));
				add(new Address(4, "Mainstreet,102", "Miami", "7852FL8588", "USA"));
			}
		};
	}

	private static List<Student> getMergedSimpleStudentAdresses() {

		var students = getSimpleStudents();
		var addresses = getSimpleAddresses();

		Random next = new Random();
		var result = students.stream().collect(Collectors.mapping((Student s) -> {
			int index = next.nextInt(0, addresses.size());
			var subList = addresses.subList(0, index);
			s.setAddresses(subList);
			return s;
		}, Collectors.toList()));

		return result;
	}

}
