package streams.parallel;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lambda.comparator.utils.Utils;

public class BasicsBadUsingParallelStream {

	public static void main(String[] args) {
		List<Integer> numbers = IntStream.rangeClosed(1, 1000).boxed().collect(Collectors.toList());

		Supplier<Integer> sequential = () -> sumSequential(numbers);

		Supplier<Integer> paralell = () -> sumParalell(numbers);

		long sequentialRuntime = observeRuntime(sequential, "Sequenzial");

		long paralellRuntime = observeRuntime(paralell, "Paralell");

		int availableProcessors = Runtime.getRuntime().availableProcessors();
		if (sequentialRuntime < paralellRuntime) {

			System.err.println(String.format(
					"Bad using paralell stream slower - %.3f%% with processors:%s with time parallel %d[ms] and with time sequential %d[ms] ",
					Utils.getDistanceDiff(sequentialRuntime, paralellRuntime), availableProcessors, paralellRuntime,
					sequentialRuntime));
		} else {
			System.out.println(String.format(
					"Good using paralell stream %.3f%% with processors:%s with time parallel %d[ms] and with time sequential %d[ms]",
					Utils.getDistanceDiff(sequentialRuntime, paralellRuntime), availableProcessors, paralellRuntime,
					sequentialRuntime));

		}
	}

	private static int sumSequential(List<Integer> numbers) {
		return numbers.stream().reduce(0, (x, y) -> x + y);
	}

	private static int sumParalell(List<Integer> numbers) {
		return numbers.stream().parallel().reduce(0, (x, y) -> x + y);
	}

	private static long observeRuntime(Supplier<Integer> s, String type) {
		long start = System.currentTimeMillis();

		System.out.printf("Result %s %d\n", type, s.get());
		return System.currentTimeMillis() - start;
	}

}
