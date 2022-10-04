package lambda.comparator.declarative;

import lambda.comparator.lambda.model.NaturalComparator;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class DeclarativeComparator {

	public static void main(String[] args) {
		var c =  new NaturalComparator<Student>();//StudentGenericComparator.getImperativeStyleComparatorFirstName(DIRECTION.DESC,
				//"getFirstName");
		var student = Utils.createStudentList();
		student.sort(c);
		student.stream().forEach(System.out::println);

		//Arrays.stream(Student.class.getMethods()).forEach(System.out::println);
	}

}
