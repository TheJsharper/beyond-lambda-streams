package streams.operation;

import static lambda.comparator.utils.Utils.buildStreamFromListStudent;
import static lambda.comparator.utils.Utils.createStudentListWithAddresses;

import java.util.IntSummaryStatistics;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lambda.comparator.lambda.model.Student;

public class BasicsSumAvg {

	public static void main(String[] args) {
		
		Supplier<Stream<Student>> s = () -> buildStreamFromListStudent(createStudentListWithAddresses());
		
		System.out.println("Summarizing Integer: "+sum(s.get()));
		
		
		System.out.println("Average: "+avg(s.get()));

	}
	
	private static IntSummaryStatistics sum(Stream<Student> students) {
		return students.collect(Collectors.summarizingInt(Student::getAge));
	}
	private static Double avg(Stream<Student> students) {
		return students.collect(Collectors.averagingInt(Student::getAge));
	}

}
