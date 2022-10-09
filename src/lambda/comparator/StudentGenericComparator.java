package lambda.comparator;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

import lambda.comparator.lambda.model.DIRECTION;
import lambda.comparator.lambda.model.MappingAnalyserGeneric;
import lambda.comparator.lambda.model.MappingResultGeneric;
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
			m = o1.getDeclaredMethod(name, parameterTypes);
			m.setAccessible(true);
		} catch (SecurityException | NoSuchMethodException e) {
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

	@SuppressWarnings("unchecked")
	public static <T> Comparator<T> getGenericComparatorLambda(Class<?> clazz, String fieldName,
			DIRECTION dir/* , boolean orderByAsc */) {

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
		if (orderByComparator != null && dir.equals(DIRECTION.DESC))
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

	@SuppressWarnings("unchecked")
	public static <T> Result<T> reducer(MappingResultGeneric<T> mappingResultP, Student students, DIRECTION dir,
			Class<?> clazz, String sortingBy) {

		Result<T> result = (MappingResultGeneric<T> mappingResult, Student student) -> {

			Object prop = isStringSortedProp(clazz, student, sortingBy);

			if (prop instanceof String) {
				resultStringProps(dir, (MappingResultGeneric<String>) mappingResult, student, prop);
			}
			if (prop instanceof Integer) {

				resultIntegerProps(dir, (MappingResultGeneric<Integer>) mappingResult, students, prop);
			}

			return mappingResult;
		};
		return result;
	}

	private static void resultStringProps(DIRECTION dir, MappingResultGeneric<String> mappingResult, Student student,
			Object prop) {
		String propString = (String) prop;

		if (propString != null && !propString.equals("") && propString.length() >= 1) {
			String key = propString.substring(0, 1).toLowerCase();
			if (!mappingResult.analyser().keySet().contains(key)) {
				MappingAnalyserGeneric<String> mapping = mappingResult.analyser().get(key);
				if (mapping == null) {
					var list = new ArrayList<Student>();
					list.add(student);
					mapping = new MappingAnalyserGeneric<String>(1, list, "", propString, false,
							new LinkedList<Boolean>(), new LinkedList<String>());
					mappingResult.analyser().put(key, mapping);
				}
			} else {
				MappingAnalyserGeneric<String> mapping = mappingResult.analyser().get(key);
				mapping.lexiSubList().add(student);
				var count = mapping.count();
				count++;
				boolean isProofOfWorkValid = dir.equals(DIRECTION.ASC) ? mapping.current().compareTo(propString) <= 0
						: mapping.current().compareTo(propString) >= 0;
				String label = "===> Proof of Work: " + isProofOfWorkValid + " Current: " + mapping.current()
						+ " Previuos: " + propString;
				mapping.proofOfWorkValidations().add(isProofOfWorkValid);
				mapping.labels().add(label);
				var newMapping = new MappingAnalyserGeneric<String>(count, mapping.lexiSubList(), mapping.current(),
						propString, isProofOfWorkValid, mapping.proofOfWorkValidations(), mapping.labels());
				mappingResult.analyser().put(key, newMapping);
			}

		}
	}

	private static void resultIntegerProps(DIRECTION dir, MappingResultGeneric<Integer> mappingResult, Student student,
			Object prop) {
		Integer propInteger = (Integer) prop;

		if (propInteger != null) {
			String key = propInteger.toString().toLowerCase();
			if (!mappingResult.analyser().keySet().contains(key)) {
				MappingAnalyserGeneric<Integer> mapping = mappingResult.analyser().get(key);
				if (mapping == null) {
					var list = new ArrayList<Student>();
					list.add(student);
					var labels = new LinkedList<String>();
					mapping = new MappingAnalyserGeneric<Integer>(1, list, Integer.valueOf(Integer.MIN_VALUE),
							propInteger, false, new LinkedList<Boolean>(), labels);
					mappingResult.analyser().put(key, mapping);
				}
			} else {
				var mapping = mappingResult.analyser().get(key);
				mapping.lexiSubList().add(student);
				var count = mapping.count();
				count++;
				boolean isProofOfWorkValid = dir.equals(DIRECTION.ASC) ? mapping.current().compareTo(propInteger) <= 0
						: mapping.current().compareTo(propInteger) >= 0;
				String label = "===> Proof of Work: " + isProofOfWorkValid + " Current: " + mapping.current()
						+ " Previuos: " + propInteger;
				mapping.proofOfWorkValidations().add(isProofOfWorkValid);
				mapping.labels().add(label);
				var newMapping = new MappingAnalyserGeneric<Integer>(count, mapping.lexiSubList(), mapping.current(),
						propInteger, isProofOfWorkValid, mapping.proofOfWorkValidations(), mapping.labels());
				mappingResult.analyser().put(key, newMapping);
			}

		}
	}

	public static Object isStringSortedProp(Class<?> clazz, Object student, String sortingBy) {
		Class<?>[] parameterTypes = new Class<?>[] {};
		Method sortMethod = StudentGenericComparator.findMethodByName(clazz, sortingBy, parameterTypes);
		Object[] parameters = new Object[] {};

		Object result = StudentGenericComparator.invokeMethd(sortMethod, parameters, student);
		return result;

	}

	public static <T> MappingResultGeneric<T> getAnalyserSortingBy(List<Student> students, DIRECTION dir,
			Class<?> clazz, String sortingBy) {

		var result = students.stream().reduce(
				new MappingResultGeneric<T>(new TreeMap<>(StudentComparatorHelper.getTreeMapComparator(dir))),
				(MappingResultGeneric<T> mappingResult, Student student) -> {

					var reducerFn = StudentGenericComparator.reducer(mappingResult, student, dir, clazz, sortingBy);
					return reducerFn.doResult(mappingResult, student);
				}, (MappingResultGeneric<T> a, MappingResultGeneric<T> b) -> b);

		return result;
	}

	public static void justTest() {
		Student instance = justSimpleStundent();
		StudentGenericComparator.isStringSortedProp(instance.getClass(), instance, "getFirstName");
	}

	public static Student justSimpleStundent() {
		return new Student("John", "due", 30, "john.due@company.com", "Male", "http://127.0.0.1");
	}

	public static void printAllNameMethodOf(Class<?> clazz) {
		Arrays.stream(clazz.getMethods()).forEach((m) -> System.out.println(m.getName()));
	}

	public static List<String> getAllDeclaredGetterMethodOf(Class<?> clazz) {
		return Arrays.stream(clazz.getDeclaredMethods()).filter((Method m) -> m.getName().startsWith("get"))
				.map((Method m) -> m.getName()).collect(Collectors.toList());
	}

	public static void printAllFieldNameMethodOf(Class<?> clazz) {
		Arrays.stream(clazz.getFields()).forEach((f) -> System.out.println(f.getName()));
	}

	public static List<String> getAllDeclaredFieldOf(Class<?> clazz) {

		return Arrays.stream(clazz.getDeclaredFields()).map((Field field) -> field.getName())
				.collect(Collectors.toList());
	}

	private static BiFunction<HashMap<String, String>, String, HashMap<String, String>> getAccumulator(
			String methodName) {

		BiFunction<HashMap<String, String>, String, HashMap<String, String>> accumulator = (
				HashMap<String, String> result, String field) -> {
			if (!result.keySet().contains(field) && methodName.length() >= 3
					&& field.toUpperCase().equals(methodName.substring(3, methodName.length()).toUpperCase())) {
				result.put(field, methodName);
			}

			return result;
		};
		return accumulator;
	}

	public static Map<String, String> getAllDeclaredMethodFieldOf(Class<?> clazz) {

		var methods = getAllDeclaredGetterMethodOf(clazz).stream();
		var fields = getAllDeclaredFieldOf(clazz);

		BinaryOperator<HashMap<String, String>> combiner = (HashMap<String, String> a, HashMap<String, String> b) -> a;

		var rs = methods.flatMap((String methodName) -> fields.stream()
				.reduce(new HashMap<String, String>(), (HashMap<String, String> result, String field) -> {
					var acc = getAccumulator(methodName);
					return acc.apply(result, field);
				}, combiner).entrySet().stream())
				.collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b));

		return rs;

	}
}

@FunctionalInterface
interface Result<T> {

	MappingResultGeneric<T> doResult(MappingResultGeneric<T> mappingResult, Student student);

}
