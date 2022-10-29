package features_since_java8.interfaces;

import static lambda.comparator.utils.Utils.*;

import java.util.Comparator;
import java.util.List;

import lambda.comparator.lambda.model.Student;

public class Main {

	private static Comparator<Student> firstNameComparator = Comparator.comparing(Student::getFirstName);
	
	private static Comparator<Student> lastNameComparator = Comparator.comparing(Student::getLastName);

	public static void main(String[] args) {
		
		List<Student> students = getSimpleStudents();
		
		sortByFirstName(students);
		
		sortByFirstLastName(students);
		
		students.add(new Student());
		
		sortByFirstNameNull(students);
		
	}

	private static void sortByFirstName(List<Student> students) {
		
		students.sort(firstNameComparator);
		
		print(students, "Sorted List of Student by Comparator.Comparing((s1, s1)->)");

	}
	private static void sortByFirstLastName(List<Student> students) {
		
		students.sort(firstNameComparator.thenComparing(lastNameComparator));
		
		print(students, "Sorted List of Student by Comparator.Comparing((s1, s1)->).thenComparator(lambda)");
		
	}
	
	private static void sortByFirstNameNull(List<Student> students) {
		
		Comparator<Student> nullFirstName = Comparator.nullsFirst((Student s1, Student s2)->{
			if(s1.getFirstName() == null || s2.getFirstName()== null) return -1;
			return s1.getFirstName().compareTo(s2.getFirstName());
		});
		
		students.sort(nullFirstName);
		
		print(students, "Sorted List of Student by Comparator.Comparing((s1, s1)->)nullable");
	}
	

}
