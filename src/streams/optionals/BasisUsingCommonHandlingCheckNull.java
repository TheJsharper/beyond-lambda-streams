package streams.optionals;

import static lambda.comparator.utils.Utils.getSimpleStudents;

import java.util.List;

import lambda.comparator.lambda.model.Student;

public class BasisUsingCommonHandlingCheckNull {

	public static void main(String[] args) {
	

		tryUsingCommonCheckNullStudentFirstName();

	}

	private static void tryUsingCommonCheckNullStudentFirstName() {
		String firstName = getStudentFirstName(getSimpleStudents());

		if (firstName != null) {
			System.out.println("---->" + firstName);
		} else
			System.err.println("----> there is not firstName rather null");

		String firstNameMaybe = getMaybeStudentFirstName();

		if (firstNameMaybe != null) {
			System.out.println("---->" + firstNameMaybe);
		} else
			System.err.println("----> there is not firstName rather null");

	}

	private static String getStudentFirstName(List<Student> students) {
		Student student = students.get(0);
		if (student != null)
			return student.getFirstName();
		else
			return null;
	}

	@SuppressWarnings("unused")
	private static String getMaybeStudentFirstName() {
		Student student = new Student();
		if (student != null)
			return student.getFirstName();
		return null;
	}

}
