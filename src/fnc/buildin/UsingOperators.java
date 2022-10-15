package fnc.buildin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingOperators {
	private static Comparator<Integer> comparator = (a, b) -> a.compareTo(b);

	public static void main(String[] args) {
		UnaryOperator<String> unaryOperator = (String input) -> input.concat(" more word....");
		System.out.println(unaryOperator.apply("Input"));
		var students = Utils.getMergedSimpleStudentAdresses();
		getAllStudentOlderThan18().apply(students).forEach(System.out::println);

		BinaryOperator<String> concatterFnc = (String arg1, String arg2) -> String.format("%s %s", arg1, arg2);
		System.out.println(concatterFnc.apply("Hello", "World"));

		BinaryOperator<Integer> sumFnc = (Integer a, Integer b) -> a + b;

		System.out.println("Sum(5, 5): 5+5= " + sumFnc.apply(5, 5));

		BinaryOperator<Integer> maxBy = BinaryOperator.maxBy(comparator);
		System.out.println("Result is: " + maxBy.apply(5, 6));

		BinaryOperator<Integer> minBy = BinaryOperator.minBy(comparator);
		System.out.println("Result is: " + minBy.apply(5, 6));

		System.out.println("--------------------------Concatting two List --------------------");
		contactStudents().apply(students, Utils.getMergedSimpleStudentAdresses()).forEach(System.out::println);

	}

	private static UnaryOperator<List<Student>> getAllStudentOlderThan18() {
		UnaryOperator<List<Student>> uOperator = (List<Student> students) -> {
			return students.stream().filter((Student s) -> {
				return s.getAge() >= 18;
			}).collect(Collectors.toList());

		};
		return uOperator;
	}

	@SuppressWarnings("serial")
	private static BinaryOperator<List<Student>> contactStudents() {

		return (List<Student> students01, List<Student> students02) -> {

			return new ArrayList<Student>() {
				{
					addAll(students01);
					addAll(students02);
				}
			};
		};
	}
}
