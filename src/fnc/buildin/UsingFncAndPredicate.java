package fnc.buildin;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingFncAndPredicate {

	public static void main(String[] args) {

		var students = Utils.getMergedSimpleStudentAdresses();

		printStudentByAge(students, 18);
		printStudentByCountryAndFirstName(students, "USA", "Max");
		printStudentByCountry(students, "MX");
		printStudentByFirstNameAndCity(students, "New York", "Max");
		printStudentByCountryName(students, "MX");

	}

	private static void printStudentByAge(List<Student> students, int age) {
		System.out.printf("----------------------Print all students by Old %d  %s", age, "--------------------\n");
		students.forEach((s) -> {
			if (isOlderThanLike(age).test(s)) {
				System.out.print(s);
				System.out.println(s.getAddresses());
			}
		});
	}

	private static void printStudentByCountryName(List<Student> students, String country) {
		System.out.printf("----------------------Print all students by Old %s  %s", country, "--------------------\n");
		students.forEach((s) -> {

			if (isThisStudentInCountry(country).test(s, s.getAddresses())) {

				System.out.print(s);
				System.out.println(s.getAddresses());
			}
		});
	}

	private static void printStudentByFirstNameAndCity(List<Student> students, String city, String firstName) {
		System.out.printf("----------------------Print all students by country %s  and Name: %s  %s", city, firstName,
				"--------------------\n");
		students.forEach((s) -> {
			s.getAddresses().forEach((a) -> {

				if (isThisStudentInLivingInCity(firstName, city).test(s, a)) {
					System.out.print(s);
					System.out.println(s.getAddresses());
				}
			});
		});
	}

	private static void printStudentByCountryAndFirstName(List<Student> students, String country, String firstName) {
		System.out.printf("----------------------Print all students by country %s  and Name: %s  %s", country,
				firstName, "--------------------\n");
		students.forEach((s) -> {
			if (isThisStudentInLivingInCountry(firstName, country).test(s, s.getAddresses())) {
				System.out.print(s);
				System.out.println(s.getAddresses());
			}
		});
	}

	private static void printStudentByCountry(List<Student> students, String country) {
		System.out.printf("----------------------Print all students by country %s  %s", country,
				"--------------------\n");
		students.forEach((s) -> {

			Predicate<List<Address>> p = isLivingInCity(country);
			var i = getAllByCountry(s).apply(p);
			i.ifPresent((student) -> {
				System.out.print("---->" + student);
				System.out.println(student.getAddresses());
			});
		});
	}

	private static Predicate<Student> isOlderThanLike(int year) {
		return (Student s) -> s.getAge() >= year;
	}

	private static Predicate<List<Address>> isLivingInCity(String city) {
		return (List<Address> addresses) -> {
			boolean isPresent = addresses.stream().filter((a) -> a.getCountry().equals(city)).findFirst().isPresent();
			return isPresent;
		};
	}

	private static BiPredicate<Student, Address> isThisStudentInLivingInCity(String firstName, String city) {
		return (Student s, Address a) -> s.getFirstName().equals(firstName) && a.getCity().equals(city);

	}

	private static BiPredicate<Student, List<Address>> isThisStudentInLivingInCountry(String firstName,
			String country) {

		BiPredicate<Student, List<Address>> isMacht = (Student s, List<Address> addresses) -> {
			boolean isPrsent = addresses.stream().filter((Address a) -> a.getCountry().equals(country)).findFirst()
					.isPresent();
			return isPrsent && s.getFirstName().equals(firstName);
		};
		return isMacht;
	}

	private static BiPredicate<Student, List<Address>> isThisStudentInCountry(String country) {

		BiPredicate<Student, List<Address>> isMacht = (Student s, List<Address> addresses) -> {
			boolean isPrsent = addresses.stream().filter((Address a) -> a.getCountry().equals(country)).findFirst()
					.isPresent();
			return isPrsent;
		};
		return isMacht;
	}

	private static Function<Predicate<List<Address>>, Optional<Student>> getAllByCountry(Student s) {

		return (Predicate<List<Address>> p) -> {
			return p.test(s.getAddresses()) ? Optional.of(s) : Optional.ofNullable(null);
		};
	}

}
