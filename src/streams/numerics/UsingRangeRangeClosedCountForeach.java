package streams.numerics;

import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class UsingRangeRangeClosedCountForeach {
	public static void main(String[] args) {

		System.out.println("count 1 to 10: " + getCountFromStream(1, 10));

		printByForeach(1, 10);

		printByRangeRangeClosed(1, 10);
	}

	private static long getCountFromStream(int from, int to) {
		return IntStream.range(from, to).count();
	}

	private static void printByForeach(int from, int to) {
		IntConsumer consumer = System.out::print;

		Supplier<IntStream> supplier = () -> IntStream.range(from, to);

		System.out.println();

		System.out.println("-------forEach-----------");

		supplier.get().forEach(consumer);

		System.out.println();

		System.out.println("-------forEachOrdered-----------");

		supplier.get().forEachOrdered(consumer);

	}

	private static void printByRangeRangeClosed(int from, int to) {
		IntConsumer consumer = System.out::print;
		
		System.out.println();

		System.out.println("-------range-----------");

		System.out.println();
		
		IntStream.range(from, to).forEach(consumer);

		System.out.println();
		
		System.out.println("-------rangeClosed-----------");

		System.out.println();
		
		System.out.println("rangeClosed from " + from + " to " + to + " " + IntStream.rangeClosed(from, to).count());

		LongStream.rangeClosed(from, to).asDoubleStream().forEach(value -> System.out.print(value + ","));

	}
}
