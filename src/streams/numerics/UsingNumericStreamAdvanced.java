package streams.numerics;

import static lambda.comparator.utils.Utils.createStudentListWithAddresses;
import static lambda.comparator.utils.Utils.print;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



import java.util.stream.IntStream;

import lambda.comparator.lambda.model.Student;

public class UsingNumericStreamAdvanced {

	public static void main(String[] args) {

		var students = createStudentListWithAddresses();
		
		var values = Arrays.asList(new Integer[] { 2, 3, 5, 6, 20, 50 });
		
		getSimpleBoxing().forEach((n) -> System.out.print(n + " ,"));
		
		System.out.println();
		
		System.out.println("Sum: " + getSinpleUnBoxing(values));
		
		print(getForceMergeStudentList(students), "getForceMergeStudentList");
	}

	private static List<Integer> getSimpleBoxing() {

		return IntStream.rangeClosed(1, 25).boxed().collect(Collectors.toList());

	}

	private static int getSinpleUnBoxing(List<Integer> integerList) {

		int sum = integerList.stream().mapToInt(Integer::intValue).sum();
		return sum;

	}

	private static List<Student> getForceMergeStudentList(List<Student> students) {

		return IntStream.range(0, students.size()).mapToObj((int n) -> students.get(n)).collect(Collectors.toList());
	}

}
