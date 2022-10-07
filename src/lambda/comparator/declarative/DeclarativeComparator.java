package lambda.comparator.declarative;

import lambda.comparator.StudentGenericComparator;
import lambda.comparator.lambda.model.DIRECTION;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class DeclarativeComparator {

	public static void main(String[] args) {

		var c = StudentGenericComparator.getGenericComparatorLambda(Student.class, "firstName", false);
		var student = Utils.createStudentList();
		student.sort(c);
		var analyserResult = StudentGenericComparator.getAnalyserSortingBy(student, DIRECTION.DESC, Student.class,
				"getFirstName");
		analyserResult.analyser().forEach((k, v) -> {
			System.out.println("Key====>  " + k + "<=========");
			v.labels().forEach(System.out::println);
		});

	}

}
