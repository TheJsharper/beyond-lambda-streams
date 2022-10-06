package lambda.comparator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.DIRECTION;
import lambda.comparator.lambda.model.MappingAnalyser;
import lambda.comparator.lambda.model.MappingResult;
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

	public static Method findMethodByName(Class<?> o1, String name, Class<?>... parameterTypes) {
		Method m = null;
		try {
			m = o1.getMethod(name, parameterTypes);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return m;
	}

	public static Object invokeMethd(Method method, Object[] parameters, Object payload) {
		Object result = null;
		try {
			result = method.invoke(payload, parameters);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			e.printStackTrace();
		}
		return result;
	}

	/*
	 * public static <T> CastType<T> getCastType(T payload) { if (payload instanceof
	 * String) { return (CastType<T>) payload; }
	 * 
	 * return null; }
	 */

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

	@SuppressWarnings("unchecked")
	public static <T> Comparator<T> orderByComparatorFnc(Class<?> clazz, String fieldName, DIRECTION dir) {
		Comparator<T> orderByComparator = (f1, f2) -> {
			Comparable<T> compA, compB;
			final Field field = getField(clazz, fieldName);
			field.setAccessible(true);
			try {
				compA = (Comparable<T>) field.get(f1);
				compB = (Comparable<T>) field.get(f2);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}

			// handling null comparators
			if (compA == null)
				return -1;
			else if (compB == null)
				return 1;

			return DIRECTION.ASC.equals(dir) ? compA.compareTo((T) compB) : compB.compareTo((T) compA);
		};
		return orderByComparator;
	}

	@SuppressWarnings("unused")
	public static <T> Result reducer(MappingResult mappingResultp, Student students, DIRECTION dir, Class<?> clazz,
			String sortingBy) {
		final Field field = getField(clazz, sortingBy);
		Class<?> type = field.getType();
		Result r = (MappingResult mappingResult, Student student) -> {

			Comparator<T> compA = orderByComparatorFnc(clazz, sortingBy, dir);
			Comparator<T> compB = orderByComparatorFnc(clazz, sortingBy, dir);

			String lastName = student.getLastName();
			if (lastName != null && !lastName.equals("") && lastName.length() >= 1) {

				lastName = lastName.substring(0, 1).toLowerCase();

				if (!mappingResult.analyser().keySet().contains(lastName)) {
					var mapping = mappingResult.analyser().get(lastName);
					if (mapping == null) {
						var list = new ArrayList<Student>();
						list.add(student);
						mapping = new MappingAnalyser(1, list, "", student.getLastName(), false,
								new LinkedList<Boolean>(), new LinkedList<String>());
						mappingResult.analyser().put(lastName, mapping);
					}
				} else {
					var mapping = mappingResult.analyser().get(lastName);
					mapping.lexiSubList().add(student);
					var count = mapping.count();
					count++;
					boolean isProofOfWorkValid = dir.equals(DIRECTION.ASC)
							? mapping.current().compareTo(student.getLastName()) <= 0
							: mapping.current().compareTo(student.getLastName()) >= 0;
					String label = "===> Proof of Work: " + isProofOfWorkValid + " Current: " + mapping.current()
							+ " Previuos: " + student.getLastName();
					mapping.proofOfWorkValidations().add(isProofOfWorkValid);
					mapping.labels().add(label);
					var newMapping = new MappingAnalyser(count, mapping.lexiSubList(), mapping.current(),
							student.getLastName(), isProofOfWorkValid, mapping.proofOfWorkValidations(),
							mapping.labels());
					mappingResult.analyser().put(lastName, newMapping);
				}
			}
			return mappingResult;
		};
		return r;
	}

	public static MappingResult getAnalyserSortingBy(List<Student> students, DIRECTION dir, String sortingBy) {

		var result = students.stream().reduce(
				new MappingResult(new TreeMap<>(StudentComparatorHelper.getTreeMapComparator(dir))),
				(MappingResult mappingResult, Student student) -> {

					// Comparator<T> comp = orderByComparatorFnc(student,sortingBy, dir);

					String lastName = student.getLastName();
					if (lastName != null && !lastName.equals("") && lastName.length() >= 1) {

						lastName = lastName.substring(0, 1).toLowerCase();

						if (!mappingResult.analyser().keySet().contains(lastName)) {
							var mapping = mappingResult.analyser().get(lastName);
							if (mapping == null) {
								var list = new ArrayList<Student>();
								list.add(student);
								mapping = new MappingAnalyser(1, list, "", student.getLastName(), false,
										new LinkedList<Boolean>(), new LinkedList<String>());
								mappingResult.analyser().put(lastName, mapping);
							}
						} else {
							var mapping = mappingResult.analyser().get(lastName);
							mapping.lexiSubList().add(student);
							var count = mapping.count();
							count++;
							boolean isProofOfWorkValid = dir.equals(DIRECTION.ASC)
									? mapping.current().compareTo(student.getLastName()) <= 0
									: mapping.current().compareTo(student.getLastName()) >= 0;
							String label = "===> Proof of Work: " + isProofOfWorkValid + " Current: "
									+ mapping.current() + " Previuos: " + student.getLastName();
							mapping.proofOfWorkValidations().add(isProofOfWorkValid);
							mapping.labels().add(label);
							var newMapping = new MappingAnalyser(count, mapping.lexiSubList(), mapping.current(),
									student.getLastName(), isProofOfWorkValid, mapping.proofOfWorkValidations(),
									mapping.labels());
							mappingResult.analyser().put(lastName, newMapping);
						}
					}
					return mappingResult;
				}, (MappingResult a, MappingResult b) -> b);

		return result;
	}

}

@FunctionalInterface
interface Result {

	MappingResult res(MappingResult mappingResult, Student student);

}
/*
 * protected interface Type<T>{
 * 
 * } public interface ICastType<T> extends Type<T>{
 * 
 * } public class CastType<T> implements ICastType<T> { private T value;
 * 
 * public CastType(T value) { this.value = value; }
 * 
 * public String getValue() { return (String)this.value; } }
 */