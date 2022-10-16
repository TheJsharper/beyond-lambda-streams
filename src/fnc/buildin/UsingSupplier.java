package fnc.buildin;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

import lambda.comparator.lambda.model.Student;
import lambda.comparator.utils.Utils;

public class UsingSupplier {

	private static Supplier<List<Student>> studentSupplier = () -> {
		return Utils.getMergedSimpleStudentAdresses();
	};
	private static IntSupplier integerSupplier = () -> {
		return 390;
	};
	private static DoubleSupplier doubleSupplier = () -> {
		return 390.45;
	};
	private static LongSupplier longSupplier = () -> {
		return 390L;
	};
	private static BooleanSupplier booleanSupplier = () -> {
		return 2 < 3;
	};

	public static void main(String[] args) {
		System.out.println(studentSupplier.get());
		System.out.println(integerSupplier.getAsInt());
		System.out.println(doubleSupplier.getAsDouble());
		System.out.println(longSupplier.getAsLong());
		System.out.println(booleanSupplier.getAsBoolean());
	}
}
