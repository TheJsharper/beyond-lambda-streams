package fnc.shorthand;

import java.util.function.Consumer;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingConsumerAsMethodRef {

	private static Consumer<Student> consumerAsMethodRefStudentToString = System.out::println;
	private static Consumer<Student> consumerAsMethodRefStudentPrintFullName = Student::printFullName;
	private static Consumer<Student> consumerAsMethodRefStudentAddresses = Student::printAddresses;

	public static void main(String[] args) {
		var students = Utils.getMergedSimpleStudentAdresses();
		students.forEach(consumerAsMethodRefStudentToString);
		students.forEach(consumerAsMethodRefStudentAddresses);
		students.forEach(consumerAsMethodRefStudentPrintFullName);

	}

}
