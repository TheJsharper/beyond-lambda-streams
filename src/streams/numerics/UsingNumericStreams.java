package streams.numerics;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class UsingNumericStreams {

	public static void main(String[] args) {
		List<Integer> values = Arrays.asList(1, 2, 3, 4, 5, 6);
		
		System.out.println("Sum by own reduce:" + sumOfNumbers(values));
		
		System.out.println("Sum by stream build-in :" + sumGeneratedRandFromTo(1, 7));
	}

	private static int sumOfNumbers(List<Integer> values) {
		return values.stream().reduce(0, (Integer acc, Integer cur) -> acc + cur);
	}

	private static int sumGeneratedRandFromTo(int from, int to) {
		return IntStream.range(from, to).sum();
	}

}
