package lambda.comparator;

import java.lang.reflect.Field;
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

	@SuppressWarnings("unchecked")
	public static <T> Comparator<T> getGenericComparatorLambda(Class<?> clazz, String fieldName, boolean orderByAsc) {

		// get field from class
		final Field field = getField(clazz, fieldName);
		field.setAccessible(true);

		// Lambda comparator
		Comparator<T> orderByComparator = (f1, f2) -> {

			Comparable<T> compA = null;
			Comparable<T> compB = null;

			// getting fields from object f1,f2
			try {
				compA = (Comparable<T>) field.get(f1);
				compB = (Comparable<T>) field.get(f2);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			// handling null comparators
			if (compA == null)
				return -1;
			else if (compB == null)
				return 1;

			return compA.compareTo((T) compB);
		};

		// reverse comparator if order is descending
		if (orderByComparator != null && !orderByAsc)
			orderByComparator = orderByComparator.reversed();

		return orderByComparator;
	}

	private static <T> Field getField(Class<T> clazz, String fieldName) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(fieldName);
		} catch (java.lang.NoSuchFieldException e) {
			e.printStackTrace();
		}
		return field;
	}

	/*
	 * public static <T> Comparator<T> orderByComparatorFnc(){ Comparator<T>
	 * orderByComparator = (f1, f2) -> { Comparable compA, compB; try { compA =
	 * (Comparable) field.get(f1); compB = (Comparable) field.get(f2); } catch
	 * (IllegalAccessException e) { throw new RuntimeException(e); } return
	 * Object.compare(compA, compB, Comparator.naturalOrder()); }; return
	 * orderByComparator; }
	 */

}
