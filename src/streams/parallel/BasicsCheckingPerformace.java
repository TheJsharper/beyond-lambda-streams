package streams.parallel;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class BasicsCheckingPerformace {

	public static void main(String[] args) {

		Supplier<Integer> supplierSumSequential = () -> sumSequential();

		Supplier<Integer> supplierSumParalell = () -> sumParalell();

		long durationSequential = checkPerformanceResult(supplierSumSequential, 20);

		long durationParalell = checkPerformanceResult(supplierSumParalell, 20);

		System.out.printf("DurationSequential : %d[ms]\n" , durationSequential);
		
		System.out.printf("DurationParalell : %d[ms]\n" , durationParalell);

	}

	private static long checkPerformanceResult(Supplier<Integer> sum, int numberOfTimes) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < numberOfTimes; i++) {
			sum.get();
		}
		long end = System.currentTimeMillis();
		return end - start;
	}

	private static int sumSequential() {
		return IntStream.rangeClosed(1, 10000000).sum();
	}

	private static int sumParalell() {
		return IntStream.rangeClosed(1, 10000000).parallel().sum();
	}

}
