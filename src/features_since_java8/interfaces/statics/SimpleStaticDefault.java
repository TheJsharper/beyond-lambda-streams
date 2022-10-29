package features_since_java8.interfaces.statics;

import java.util.Arrays;
import java.util.List;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class SimpleStaticDefault {

	public static void main(String[] args) {
		List<Student> simpleStudents = Utils.getSimpleStudents();

		IMultiplier data = new MultiplierImpl();

		System.out.println("List of student length: " + IMultiplier.size(simpleStudents));

		data.printAllStudent(simpleStudents);

		double result = data.multiply(Arrays.asList(1, 2, 3, 5, 6, 7, 8, 9));

		System.out.println("Sum ===>" + result);

		simpleStudents.clear();

		System.out.println("Is List Empty? : " + IMultiplier.isEmpty(simpleStudents));
	}

}

interface IMultiplier {

	double multiply(List<Integer> numbers);

	default void printAllStudent(List<Student> students) {
		students.forEach(System.out::println);
	}

	static long size(List<Student> students) {
		return students.size();
	}

	static boolean isEmpty(List<Student> students) {
		return students != null && students.size() == 0;
	}
}

class MultiplierImpl implements IMultiplier {

	@Override
	public double multiply(List<Integer> numbers) {

		return numbers.stream().reduce(1, (a, b) -> a * b);
	}
}
