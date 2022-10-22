package streams.operation.terminal;

import static lambda.comparator.utils.Utils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class UsingAggregateSumAvg {

	public static void main(String[] args) {
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());

		System.out.println("Sum of Age by 30 years: " + getSumStudentByAge(s.get(), 30));

		getStudentSumByAge(s).forEach((k, v) -> System.out.println("Age: " + k + " Sum:" + v));

		System.out.println("Sum of Address List by 28: " + getSumAddressesOfStudentByAge(s.get(), 30));

		System.out.println("Average of student ages: " + getAverageAgeOfStudents(s.get()));

		getOwnCalculationStudentCountByAge(s).forEach((k, v) -> System.out.println("Age: " + k + " Count:" + v));
		
		getCalculationStudentCountByAge(s).forEach((k, v) -> System.out.println("Age: " + k + " Count:" + v));
	}

	private static int getSumStudentByAge(Stream<Student> students, int age) {
		return students.filter((Student s) -> s.getAge() == age).collect(Collectors.summingInt(Student::getAge));
	}

	private static Map<Integer, Integer> getStudentSumByAge(Supplier<Stream<Student>> supplier) {

		return supplier.get().reduce(new HashMap<Integer, Integer>(), (HashMap<Integer, Integer> acc, Student s) ->

		reducer(acc, s, supplier), (a, b) -> a);
	}

	private static Map<Integer, Integer> getOwnCalculationStudentCountByAge(Supplier<Stream<Student>> supplier) {

		return supplier.get().reduce(new HashMap<Integer, Integer>(), UsingAggregateSumAvg::reducer, (a, b) -> a);
	}

	private static Map<Integer, Integer> getCalculationStudentCountByAge(Supplier<Stream<Student>> supplier) {

		return supplier.get().reduce(new HashMap<Integer, Integer>(), (HashMap<Integer, Integer> acc, Student s) -> {
			if (!acc.containsKey(s.getAge())) {
				var value = supplier.get().filter((Student student) -> s.getAge() == student.getAge())
						.collect(Collectors.summingInt(Student::getAge));
				acc.put(s.getAge(), value);
			}
			return acc;
		}, (a, b) -> a);
	}

	private static HashMap<Integer, Integer> reducer(HashMap<Integer, Integer> acc, Student s) {
		if (!acc.containsKey(s.getAge())) {
			acc.put(s.getAge(), 1);
		} else {
			var value = acc.get(s.getAge());
			value++;
			acc.put(s.getAge(), value);

		}
		return acc;
	}

	private static HashMap<Integer, Integer> reducer(HashMap<Integer, Integer> acc, Student s,
			Supplier<Stream<Student>> supplier) {
		if (!acc.containsKey(s.getAge())) {

			var sum = supplier.get().filter((Student student) -> student.getAge() == s.getAge())
					.collect(Collectors.summingInt(Student::getAge));
			acc.put(s.getAge(), sum);

		}
		return acc;
	}

	private static long getSumAddressesOfStudentByAge(Stream<Student> students, int age) {
		return students.filter((Student s) -> s.getAge() == age)
				.collect(Collectors.summingLong((Student s) -> s.getAddresses().size()));
	}

	private static Double getAverageAgeOfStudents(Stream<Student> students) {
		return students.collect(Collectors.averagingDouble(Student::getAge));

	}

}
