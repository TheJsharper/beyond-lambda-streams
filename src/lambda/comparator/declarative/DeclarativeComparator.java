package lambda.comparator.declarative;

import static lambda.comparator.StudentGenericComparator.getAllDeclaredMethodFieldOf;
import static lambda.comparator.StudentGenericComparator.getAnalyserSortingBy;
import static lambda.comparator.StudentGenericComparator.getGenericComparatorLambda;

import java.util.List;

import lambda.comparator.lambda.model.DIRECTION;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class DeclarativeComparator {

	public static void main(String[] args) {

		var student = Utils.createStudentList();
		getAllDeclaredMethodFieldOf(Student.class)
				.forEach((key, value) -> printAnalyserBy(student, value, key, DIRECTION.ASC));
	}

	private static void printAnalyserBy(List<Student> student, String propName, String fieldName, DIRECTION dir) {

		System.out.printf("----------------------------------------- Test By %s  %s", fieldName.toUpperCase(),
				"--------------------------------------\n");
		var c = getGenericComparatorLambda(Student.class, fieldName, dir);
		student.sort(c);
		var analyserResult = getAnalyserSortingBy(student, dir, Student.class, propName);
		analyserResult.analyser().forEach((k, v) -> {
			System.out.println("Key====>  " + k + "<=========");
			v.labels().forEach(System.out::println);
		});
	}

}
