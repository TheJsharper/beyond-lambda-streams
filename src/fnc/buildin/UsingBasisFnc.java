package fnc.buildin;

import java.util.function.BiFunction;
import java.util.function.Function;

public class UsingBasisFnc {

	private static Function<String, String> inputUppercase = (String input) -> input.toUpperCase();
	private static Function<String, String> greeting = (String name) -> "Hello " + name.toUpperCase() + " !";
	private static Function<String, Integer> characterCounter = (String simpleString) -> simpleString == null ? 0
			: simpleString.length();

	private static BiFunction<Integer, Integer, Integer> adder = (Integer a, Integer b) -> a + b;
	private static BiFunction<Integer, Integer, Integer> substract = (Integer a, Integer b) -> a - b;
	private static BiFunction<Integer, Integer, Integer> multiplier = (Integer a, Integer b) -> a * b;
	private static BiFunction<Double, Double, Double> divider = (Double a, Double b) -> a / b;

	public static void main(String[] args) {
		System.out.println("===>" + inputUppercase.apply("hello world"));
		System.out.println("===>" + greeting.apply("John"));
		System.out.println("===>Length: " + characterCounter.apply("A simple word"));
		Function<String, String> rely = Function.identity();
		System.out.println("===>" + rely.apply("hey, can you read me?"));
		System.out.println("----------------- Using BiFunction -----------------------");
		System.out.println(" sum(5, 3)" + adder.apply(5, 3));
		System.out.println(" substract(5, 3)" + substract.apply(5, 3));
		System.out.println(" multiplier(5, 3)" + multiplier.apply(5, 3));
		System.out.println(" divider(5.0, 3.5)" + divider.apply(5.0, 3.5));
	}

}
