package fnc.buildin;

import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;

import lambda.comparator.lambda.model.Student;

public class UsingBasisFnc {

	private static Function<String, String> inputUppercase = (String input) -> input.toUpperCase();
	private static Function<String, String> greeting = (String name) -> "Hello " + name.toUpperCase() + " !";
	private static Function<String, Integer> characterCounter = (String simpleString) -> simpleString == null ? 0
			: simpleString.length();

	private static BiFunction<Integer, Integer, Integer> adder = (Integer a, Integer b) -> a + b;
	private static BiFunction<Integer, Integer, Integer> substract = (Integer a, Integer b) -> a - b;
	private static BiFunction<Integer, Integer, Integer> multiplier = (Integer a, Integer b) -> a * b;
	private static BiFunction<Double, Double, Double> divider = (Double a, Double b) -> a / b;

	private static IntToLongFunction intToLongfnc = (int value) -> value * 1000000;
	private static IntToDoubleFunction intToDoublefnc = (int value) -> value * 3.14;

	private static DoubleToIntFunction doubleToIntfnc = (double value) -> (int) value * 3;
	private static DoubleToLongFunction doubleToLongfnc = (double value) -> (long) value * 3;

	private static LongToIntFunction longToLongfnc = (long value) -> (int) value * 3;
	private static LongToDoubleFunction longToDoublefnc = (long value) -> (double) value * 3.14;

	private static DoubleFunction<String> doublefnc = (double value) -> new Student("TestFirstName", "TestLastName", 25,
			"info@student.com", "male", "http://127.0.0.1").toString();
	private static IntFunction<Student> intfnc = (int value) -> new Student("TestFirstName", "TestLastName", value,
			"info@student.com", "male", "http://127.0.0.1");
	private static LongFunction<Student> longfnc = (long value) -> new Student("TestFirstName", "TestLastName",
			(int) value, "info@student.com", "male", "http://127.0.0.1");

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

		System.out.println(" IntToLongFnc(50000)" + intToLongfnc.applyAsLong(50000));
		System.out.println(" IntToDoubleFnc(50000)" + intToDoublefnc.applyAsDouble(50000));

		System.out.println(" DoubleToIntFnc(50000.45)" + doubleToIntfnc.applyAsInt(50.1515));
		System.out.println(" DoubleToLongFnc(50000.45)" + doubleToLongfnc.applyAsLong(50.1515));
		
		
		System.out.println(" LongToIntFunction(50000.45)" + longToLongfnc.applyAsInt(50000000L));
		System.out.println(" LongToDoubleFunction(50000.45)" + longToDoublefnc.applyAsDouble(50000000L));
		
		
		System.out.println(" DoubleFunction<String>(50000.45)" + doublefnc.apply(5000D));
		System.out.println(" IntFunction<Student>(50000.45)" + intfnc.apply(5000));
		System.out.println(" LongFunction<Student> (50000.45)" + longfnc.apply(5000));
		
		
	}

}
