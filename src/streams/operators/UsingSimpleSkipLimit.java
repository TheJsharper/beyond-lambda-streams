package streams.operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsingSimpleSkipLimit {

	public static void main(String[] args) {

		var values = Arrays.asList(new Integer[] { 5, 7, 50, 4, 2, 1, -1, 100, 2 });

		printInteger(getSimpleLimitedList(values, 3), "getSimpleLimitedList");

		printInteger(getSimpleSkippedList(values, 3), "getSimpleSkippedList");

		printInteger(getSimplePagingList(values, 3, 9), "getSimplePagingList");

		sumFromStartTo(values, 3).ifPresentOrElse(
				(Integer sumValue) -> System.out.println(" Total sum from index 3: " + sumValue),
				() -> System.err.println("choosen index out the array"));

		sumFromToEnd(values, 3).ifPresentOrElse(
				(Integer sumValue) -> System.out.println(" Total sum from index 3: " + sumValue),
				() -> System.err.println("choosen index out the array"));

		sumFromTo(values, 3, 5).ifPresentOrElse(
				(Integer sumValue) -> System.out.println(" Total sum from index 3 to 8: " + sumValue),
				() -> System.err.println("choosen index out the array"));

	}

	private static List<Integer> getSimpleLimitedList(List<Integer> values, int top) {
		return top > 0 && top <= values.size() ? values.stream().limit(top).collect(Collectors.toList())
				: new ArrayList<Integer>();
	}

	private static List<Integer> getSimpleSkippedList(List<Integer> values, int from) {
		return from > 0 && from <= values.size() ? values.stream().skip(from).collect(Collectors.toList())
				: new ArrayList<Integer>();
	}

	private static List<Integer> getSimplePagingList(List<Integer> values, int from, int to) {
		return from > 0 && from <= values.size() && to > 0 && to <= values.size() && from <= to
				? values.stream().skip(from).limit(to).collect(Collectors.toList())
				: new ArrayList<Integer>();
	}

	private static Optional<Integer> sumFromStartTo(List<Integer> values, int to) {
		return to > 0 && to <= values.size()
				? values.stream().limit(to).reduce((Integer acc, Integer next) -> acc + next)
				: Optional.empty();
	}

	private static Optional<Integer> sumFromToEnd(List<Integer> values, int from) {
		return from > 0 && from <= values.size()
				? values.stream().skip(from).reduce((Integer acc, Integer next) -> acc + next)
				: Optional.empty();
	}

	private static Optional<Integer> sumFromTo(List<Integer> values, int from, int to) {
		return from > 0 && from <= values.size() && to > 0 && to <= values.size() && from <= to
				? values.stream().skip(from).limit(to).reduce((Integer acc, Integer next) -> acc + next)
				: Optional.empty();
	}

	private static void printInteger(List<Integer> students, String label) {
		System.out.printf("----------------------- %s %s\n", label.toUpperCase(), "---------------------------------");
		students.forEach(UsingSimpleSkipLimit::printInteger);

	}

	private static void printInteger(Integer student) {
		System.out.println(student);

	}

}
