package lambda.comparator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.DIRECTION;
import lambda.comparator.lambda.model.Student;

public class StudentGenericComparator {

	public static Comparator<Student> getImperativeStyleComparatorFirstName(DIRECTION dir, String propertyName) {
		return new Comparator<Student>() {

			@Override
			public int compare(Student o1, Student o2) {
				var studentClass1 = StudentGenericComparator.findMethod(o1.getClass(), propertyName);
				var studentClass2 = StudentGenericComparator.findMethod(o2.getClass(), propertyName);

				Object propValueOne = null;
				Object propValueTwo = null;

				try {
					propValueOne = studentClass1.invoke(o1.getClass(), new Object[] { null });
					propValueTwo = studentClass2.invoke(o1.getClass(), new Object[] { null });

					var compareTo = StudentGenericComparator.findMethod(propValueOne.getClass(), "CompareTo");
					if (dir.equals(DIRECTION.ASC)) {

						var ret = compareTo.invoke(propValueOne.getClass(), propValueTwo.getClass());
						return (Integer) ret;
					} else {
						var ret = compareTo.invoke(propValueTwo.getClass(), propValueTwo.getClass());
						return (Integer) ret;
					}

				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

					e.printStackTrace();
				}
				return 0;

			}
		};
	}

	private static Method findMethod(Class<?> o1, String name) {
		return Arrays.stream(o1.getMethods()).filter(m -> m.getName().equals(name)).collect(Collectors.toList()).get(0);
	}

}

