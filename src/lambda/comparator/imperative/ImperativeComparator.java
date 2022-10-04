package lambda.comparator.imperative;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

import lambda.comparator.StudentComparatorHelper;
import lambda.comparator.lambda.model.DIRECTION;
import lambda.comparator.lambda.model.MappingAnalyser;
import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

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
		var students = Utils.createStudentList();
		var comparator = StudentComparatorHelper.getImperativeStyleComparatorFirstName(d);
		students.sort(comparator);

		return students;
	}

	private static List<Student> getSortedListOfSudentsByLastName(DIRECTION d) {
		var students = Utils.createStudentList();
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

}
