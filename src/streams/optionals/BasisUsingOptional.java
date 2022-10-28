package streams.optionals;

import java.util.Optional;

import lambda.comparator.lambda.model.Student;

public class BasisUsingOptional {

	public static void main(String[] args) {
		
		tryUsingOptionalWrapper();
	}

	
	private static void tryUsingOptionalWrapper() {
		Optional<String> firstNameOptional = getFirstNameOptional();
		
		if(firstNameOptional.isPresent()) {
			System.out.println("Student FirstName is :"+firstNameOptional.get());
		}else {
			System.err.println("----> there is not firstName rather null");
		}
		
		Optional<String> maybeValueFirstNameOptional = getMaybeValueFirstNameOptional();
		
		if(maybeValueFirstNameOptional.isPresent()) {
			System.out.println("Student FirstName is :"+firstNameOptional.get());
			
		}else {
			System.err.println("----> there is not firstName rather null");
			
		}
	}
	private static Optional<String> getFirstNameOptional() {
		Optional<Student> student = Optional
				.ofNullable(new Student("Max", "Bauer", 20, "max.bauer@domain.com", "male", null));
		
		if (student.isPresent()) {
			return student.map(Student::getFirstName);
		} else {
			return Optional.empty();
		}

	}
	private static Optional<String> getMaybeValueFirstNameOptional() {
		Optional<Student> student = Optional
				.ofNullable(new Student());
		
		if (student.isPresent()) {
			return student.map(Student::getFirstName);
		} else {
			return Optional.empty();
		}
		
	}

}
