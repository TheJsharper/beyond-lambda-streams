package streams.optionals;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Address;
import lambda.comparator.lambda.model.Student;
import static lambda.comparator.utils.Utils.*;

public class BasicsOptionalMapFlatMap {

	public static void main(String[] args) {

		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(getSimpleStudents());

		Supplier<Stream<Student>> supplier = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		usingOptionalFlatMapEmpty(s.get());

		usingOptionalFlatMap(supplier.get());
		
		usingOptionalMap();
		
		usingOptionalMapEmpty();
		
		usingOptionalFilter();
		
		usingOptionalFilterEmpty();
	}

	private static void usingOptionalFlatMapEmpty(Stream<Student> students) {

		Optional<Student> studentOptional = students.findFirst();

		if (studentOptional.isPresent()) {
			Optional<Address> addressOptional = studentOptional
					.flatMap((Student s) -> s.getAddresses().stream().findFirst());

			System.out.println("====>" + addressOptional);
		}
	}

	private static void usingOptionalMap() {

		Optional<Student> studentOptional = Optional
				.of(new Student("Max", "Bauer", 20, "max.bauer@domain.com", "male", null));

		if (studentOptional.isPresent()) {
			Optional<String> addressOptional = studentOptional
					.map(Student::getFirstName);

			System.out.println("====>" + addressOptional);
		}
	}
	private static void usingOptionalMapEmpty() {
		
		Optional<Student> studentOptional = Optional
				.of(new Student());
		
		if (studentOptional.isPresent()) {
			Optional<String> addressOptional = studentOptional
					.map(Student::getFirstName);
			
			System.out.println("====>" + addressOptional);
		}
	}
	private static void usingOptionalFilter() {
		
		Optional<Student> studentOptional = Optional
				.of(new Student("Max", "Bauer", 20, "max.bauer@domain.com", "male", null));
		
		if (studentOptional.isPresent()) {
			Optional<Student> stuOptional = studentOptional
					.filter((Student s)-> s.getAge() >=20);
			
			System.out.println("====>" + stuOptional);
		}
	}
	private static void usingOptionalFilterEmpty() {
		
		Optional<Student> studentOptional = Optional
				.of(new Student());
		
		if (studentOptional.isPresent()) {
			Optional<Student> stuOptional = studentOptional
					.filter((Student s)-> s.getAge() >=20);
			
			System.out.println("====>" + stuOptional);
		}
	}

	private static void usingOptionalFlatMap(Stream<Student> students) {

		Optional<Student> studentOptional = students.findFirst();

		if (studentOptional.isPresent()) {
			Optional<Address> addressOptional = studentOptional
					.flatMap((Student s) -> s.getAddresses().stream().findFirst());

			System.out.println("====>" + addressOptional);
		}
	}

}
