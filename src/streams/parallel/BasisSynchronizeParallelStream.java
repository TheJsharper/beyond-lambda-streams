package streams.parallel;

import java.util.stream.IntStream;

public class BasisSynchronizeParallelStream {

	public static void main(String[] args) {

		storeSumUnsynchronized();
		storeSumSynchronized();
	}

	private static void storeSumUnsynchronized() {

		var sum = new Sum();
		IntStream.rangeClosed(1, 1000).parallel().forEach(sum::performSum);

		System.out.println("Total:" + sum.getTotal());

	}
	private static void storeSumSynchronized() {
		
		var sum = new Sum();
		IntStream.rangeClosed(1, 1000).parallel().forEach(sum::synchronizedPerformSum);
		
		System.out.println("Total:" + sum.getTotal());
		
	}

}

class Sum {
	private int total;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void performSum(int input) {
		total += input;
	}

	synchronized public void synchronizedPerformSum(int input) {
		total += input;
	}

}
