package fnc.shorthand;

import java.util.List;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingBasisMethodRef {

	public static void main(String[] args) {
		List<Student> students = Utils.createStudentList();
		students.sort(/* here we call the local static method of this class */UsingBasisMethodRef::sortByFirstName);
		students.forEach(/* here we call as shorthand println() parameter students */System.out::println);
	}

	/**
	 * this is callee method for sorting student list
	 * 
	 * @param s1 Student for considering firstName
	 * @param s2 Student for considering firstName
	 * @return lexically different between of StudentS1.firstName and
	 *         StudentS2.firstName
	 */
	private static int sortByFirstName(Student s1, Student s2) {
		return s1.getFirstName().compareTo(s2.getFirstName());
	}
}
