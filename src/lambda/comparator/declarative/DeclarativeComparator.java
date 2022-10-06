package lambda.comparator.declarative;

import java.lang.reflect.Method;
import java.util.ArrayList;

import lambda.comparator.StudentGenericComparator;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class DeclarativeComparator {

	public static void main(String[] args) {

		var c = StudentGenericComparator.getGenericComparatorLambda(Student.class, "age", false); // new
																									// NaturalComparator<Student>();//StudentGenericComparator.getImperativeStyleComparatorFirstName(DIRECTION.DESC,
		// "getFirstName");
		var student = Utils.createStudentList();
		student.sort(c);
		// student.stream().forEach(System.out::println);

		// Arrays.stream(Student.class.getMethods()).forEach(System.out::println);
		String str = "Heeelo World";
		Class<?>[] parameterTypes = new Class<?>[] { int.class, int.class };
		new ArrayList<String>();
		Object[] parameters = new Object[] { 1, str.length() };
		Method m = StudentGenericComparator.findMethodByName(String.class, "substring", parameterTypes);
		String typeName = str.getClass().getTypeName();
		Object result = (String) StudentGenericComparator.invokeMethd(m, parameters, str);
		System.out.println("REsult: ==> " + result + " TYpe " + typeName);
		// var value =StudentGenericComparator.getCastType(str);
		// String t =value.getValue();
	}

}
