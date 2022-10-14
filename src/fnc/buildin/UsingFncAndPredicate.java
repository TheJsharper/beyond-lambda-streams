package fnc.buildin;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingFncAndPredicate {

	public static void main(String[] args) {

		var students = Utils.getMergedSimpleStudentAdresses();
		students.forEach((s) -> {
			if (isThisStudentInLivingInCountry("Max", "USA").test(s, s.getAddresses())) {
				System.out.print(s);
				System.out.println(s.getAddresses());
			}
		});
		
		Supplier<Stream<List<Student>>> s = ()->{
			return Stream.of(students);
		};
		
		s.get().map((ss)->{
			var ret =ss.stream().map(getMapStudentByCountry("USA"));
			return null;
		});
		//students.stream().flatMap(null)
	}

	private static Predicate<Student> isOlderThanLike(int year) {
		return (Student s) -> s.getAge() >= year;
	}

	private static Predicate<Address> isLivingInCity(String city) {
		return (Address a) -> a.getCity().equals(city);
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
	
	private static Function<Student, String> getMapStudentByCountry( String country){

		return (Student s )-> {
			s.getAddresses().stream().filter((a)-> a.getCountry().equals(country)).findFirst().isPresent();
			return "";
		};
	}
}
