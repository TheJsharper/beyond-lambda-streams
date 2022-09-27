package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

public class ImperativeVsDeclarativeApproach {

	public static void main(String[] args) {

		// We want to show you based on sum of integers from 0 => 100

		/**
		 * Imperative approach
		 */
		int sumImperativeStyle = getImperativeStyle();
		System.out.println("Sum is : " + sumImperativeStyle);

		/**
		 * Declarative approach
		 */
		int sumDeclarativeStyle = getDeclarativeStyle();
		System.out.println("Sum is : " + sumDeclarativeStyle);

		/**
		 * Declarative approach
		 */
		int sumImperativeStyleParalell = getImperativeStyleParalell();
		System.out.println("Sum is : " + sumImperativeStyleParalell);

		/**
		 * Declarative approach
		 */
		int sumDeclarativeParalell = getDeclarativeParalell();
		System.out.println("Sum is : " + sumDeclarativeParalell);

	}

	/**
	 * @return
	 */
	private static int getImperativeStyle() {

		int sum = 0;

		for (int i = 0; i <= 1000; i++) {
			sum += i;
		}
		return sum;

	}

	private static int getDeclarativeStyle() {
		int sum = IntStream.rangeClosed(0, 1000).sum();
		return sum;
	}

	private static int getImperativeStyleParalell() {
		ExecutorService executor = Executors.newFixedThreadPool(8);
		var obj = new Object();
		List<Future<Integer>> tasks = new ArrayList<>();
		int block = 1000 / 8;
		for (int i = 0; i < 1000; i += block) {
			var task = executor.submit(new SumParalell(i, (block + i)));
			tasks.add(task);
		}

		int sum = 0;
		for (Future<Integer> fut : tasks) {

			try {
				synchronized (obj) {
					Integer value = fut.get();
					sum += value;
				}

			} catch (InterruptedException | ExecutionException e) {

				e.printStackTrace();
			}
		}
		executor.shutdown();
		return sum;
	}

	private static int getDeclarativeParalell() {
		int sum = IntStream.rangeClosed(0, 1000).parallel().sum();
		return sum;
	}

}

class SumParalell implements Callable<Integer> {
	private int from;
	private int to;

	public SumParalell(int from, int to) {
		this.from = from;
		this.to = to;
	}

	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for (int i = from + 1; i <= to; i++) {
			sum += i;
		}
		return sum;
	}

}
