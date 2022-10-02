package test.comparator.lambada;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ImperativeVsDeclarativeComparator {
	public static void main(String[] args) {
		System.out.println("Imperative Style");

		getResultCompareMethod("Imperative");

		System.out.println("Declarative Style");
		getResultCompareMethod("Declartive");

		var t = getSortedListOfSudentsByFirstName(Direction.ASC).stream().parallel()
				.reduce(new MappingResult(new HashMap<>()), (a, b) -> {
					String firstName = b.getFirstName();
					if (firstName != null && !firstName.equals("") && firstName.length() >= 1) {

						firstName = firstName.substring(0, 1).toLowerCase();

						if (!a.lexiSubList().keySet().contains(firstName)) {
							var list = a.lexiSubList().get(firstName);
							if (list == null) {
								list = new ArrayList<>();
								list.add(b);
								a.lexiSubList().put(firstName, list);

							}
						} else {
							var list = a.lexiSubList().get(firstName);
							list.add(b);
							a.lexiSubList().put(firstName, list);

						}
					}
					return a;
				}, (MappingResult a, MappingResult b) -> {
					return a;
				});
		// getSortedListOfSudentsByFirstName(Direction.DESC).forEach(System.out::println);

		t.lexiSubList().forEach((k, v) -> {
			System.out.println("KEY===>" + k);
			v.forEach(System.out::println);
		});

	}

	private static List<Student> getSortedListOfSudentsByFirstName(Direction d) {
		var students = createStudentList();
		var comparator = StudentComparatorHelper.getImperativeStyleComparatorFirstName(d);
		students.sort(comparator);

		return students;
	}

	private static void getResultCompareMethod(String styleName) {
		if (styleName.equals("Imperative")) {

			Comparator<Integer> comparator = getComparatorImperativeStyle();
			System.out.println("FirstParameter is less than SecondParameter:  " + comparator.compare(10, 11));
			System.out.println("FirstParameter is greater than SecondParameter:  " + comparator.compare(11, 10));
			System.out.println("FirstParameter is equal SecondParameter:  " + comparator.compare(11, 11));
		} else {
			Comparator<Integer> comparator = getComparatorDeclarativeStyle();
			System.out.println("FirstParameter is less than SecondParameter:  " + comparator.compare(10, 11));
			System.out.println("FirstParameter is greater than SecondParameter:  " + comparator.compare(11, 10));
			System.out.println("FirstParameter is equal SecondParameter:  " + comparator.compare(11, 11));

		}
	}

	private static Comparator<Integer> getComparatorImperativeStyle() {

		Comparator<Integer> intComparator = new Comparator<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				return o1.compareTo(o2);
			}
		};
		return intComparator;
	}

	private static Comparator<Integer> getComparatorDeclarativeStyle() {

		return (Integer o1, Integer o2) -> o1.compareTo(o2);
	}

	private static List<Student> createStudentList() {
		ObjectMapper mapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, Visibility.NONE)
				.setVisibility(PropertyAccessor.GETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.SETTER, Visibility.PUBLIC_ONLY)
				.setVisibility(PropertyAccessor.CREATOR, Visibility.PUBLIC_ONLY);
		CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(List.class, Student.class);

		File initialFile = new File("resources/persons.json");
		List<Student> students = new ArrayList<>();
		try {
			InputStream targetStream = new FileInputStream(initialFile);
			students = mapper.readValue(targetStream, collectionType);
			// students.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return students;
	}

}

record MappingResult(Map<String, List<Student>> lexiSubList) {
};

interface IComparatorStudentProp {
	Comparator<Student> compareWithDirection(Direction dir);
}