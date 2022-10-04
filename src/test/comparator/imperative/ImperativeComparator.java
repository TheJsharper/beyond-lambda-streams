package test.comparator.imperative;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import test.comparator.lambada.StudentComparatorHelper;
import test.comparator.lambada.model.DIRECTION;
import test.comparator.lambada.model.MappingAnalyser;
import test.comparator.lambada.model.Student;

public class ImperativeComparator {
	public static void main(String[] args) {
		System.out.println("Imperative Style");

		getResultCompareMethod("Imperative");

		System.out.println("Declarative Style");
		getResultCompareMethod("Declartive");

		printAnalyserSortingByFirstName(DIRECTION.ASC);
		printAnalyserSortingByLastName(DIRECTION.DESC);

	}

	private static void printAnalyserSortingByFirstName(DIRECTION dir) {
		var studentsSortedByFirstName = StudentComparatorHelper
				.getAnalyserSortingByFirstName(getSortedListOfSudentsByFirstName(dir), dir);

		for (Map.Entry<String, MappingAnalyser> entry : studentsSortedByFirstName.analyser().entrySet()) {
			System.out.println("KEY===>" + entry.getKey() + " Count ==>" + entry.getValue().count() + " Previous: "
					+ entry.getValue().previous() + " Current: " + entry.getValue().current());

			for (String label : entry.getValue().labels()) {
				System.out.println(label);
			}

		}

	}

	private static void printAnalyserSortingByLastName(DIRECTION dir) {
		var studentsSortedByLastName = StudentComparatorHelper
				.getAnalyserSortingByLastName(getSortedListOfSudentsByLastName(dir), dir);

		for (Map.Entry<String, MappingAnalyser> entry : studentsSortedByLastName.analyser().entrySet()) {
			System.out.println("KEY===>" + entry.getKey() + " Count ==>" + entry.getValue().count() + " Previous: "
					+ entry.getValue().previous() + " Current: " + entry.getValue().current());

			for (String label : entry.getValue().labels()) {
				System.out.println(label);
			}

		}

	}

	private static List<Student> getSortedListOfSudentsByFirstName(DIRECTION d) {
		var students = createStudentList();
		var comparator = StudentComparatorHelper.getImperativeStyleComparatorFirstName(d);
		students.sort(comparator);

		return students;
	}

	private static List<Student> getSortedListOfSudentsByLastName(DIRECTION d) {
		var students = createStudentList();
		var comparator = StudentComparatorHelper.getImperativeStyleComparatorLastName(d);
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
		} catch (IOException e) {
			e.printStackTrace();
		}
		return students;
	}

}
