package features_since_java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static lambda.comparator.utils.Utils.*;

public class BasicsAboutInterface {

	public static void main(String[] args) {

		print(usingOldFeatureSortOfCollections(),
				"-------------------Sorted list using Class Collections.Sort()----------------");

		print(usingNewFeatureSortOfListDefaultMethod(),
				"-------------------Sorted list using interface default sort method----------------");

		print(usingNewFeatureSortReversedOfListDefaultMethod(),
				"-------------------Sorted reversing list using interface default sort method----------------");
	}

	private static List<String> usingOldFeatureSortOfCollections() {
		List<String> persons = Arrays.asList("Eric", "Jenny", "Alex", "Dan", "Mike", "Max");

		Collections.sort(persons);

		return persons;

	}

	private static List<String> usingNewFeatureSortOfListDefaultMethod() {
		List<String> persons = Arrays.asList("Eric", "Jenny", "Alex", "Dan", "Mike", "Max");

		persons.sort(Comparator.naturalOrder());
		return persons;

	}

	private static List<String> usingNewFeatureSortReversedOfListDefaultMethod() {
		List<String> persons = Arrays.asList("Eric", "Jenny", "Alex", "Dan", "Mike", "Max");

		persons.sort(Comparator.reverseOrder());
		return persons;

	}

}
