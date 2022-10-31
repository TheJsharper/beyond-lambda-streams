package features_since_java8.interfaces.dates;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {

		printAllnewClazzes();

		printLocalDateIntances();

		printLocalDateMethods();
	}

	private static void printAllnewClazzes() {
		LocalDate localDate = LocalDate.now();

		LocalTime localTime = LocalTime.now();

		LocalDateTime localDateTime = LocalDateTime.now();

		System.out.printf("Just local date ---> %s\n", localDate);
		System.out.printf("Just local time ---> %s\n", localTime);
		System.out.printf("Local date and time ---> %s\n", localDateTime);
	}

	private static void printLocalDateIntances() {

		LocalDate now = LocalDate.now();

		LocalDate of = LocalDate.of(2022, 10, 31);

		System.out.printf("Instance by now() vs of() %s <--vs--> %s \n", now, of);

	}

	private static void printLocalDateMethods() {

		LocalDate now = LocalDate.now();

		System.out.printf("localDate.getMonth() as String--->%s <-----\n", now.getMonth());

		System.out.printf("localDate.getMonthValue() as int--->%s <-----\n", now.getMonthValue());

		System.out.printf("localDate.getDayOfMonth() as int--->%s <-----\n", now.getDayOfMonth());

		System.out.printf("localDate.getDayOfWeek() --->%s <-----\n", now.getDayOfWeek());

		System.out.printf("localDate.getDayOfYear() --->%s <-----\n", now.getDayOfYear());

		System.out.printf("localDate.getYear() --->%s <-----\n", now.getYear());

		List<String> listNames = getNames(ChronoField.class);

		getResult(listNames, now).forEach((k, v) -> System.out.println(k + " value " + v));

	}

	private static List<String> getNames(Class<? extends Enum<?>> e) {
		return Arrays.stream(e.getEnumConstants()).map(Enum::name)
				.filter((String name) -> !name.equals("NANO_OF_SECOND") && !name.equals("NANO_OF_DAY")
						&& !name.equals("MICRO_OF_SECOND") && !name.equals("MICRO_OF_DAY")
						&& !name.equals("MILLI_OF_SECOND") && !name.equals("MILLI_OF_DAY")
						&& !name.equals("SECOND_OF_MINUTE") && !name.equals("SECOND_OF_DAY")
						&& !name.equals("MINUTE_OF_HOUR") && !name.equals("MINUTE_OF_DAY")
						&& !name.equals("HOUR_OF_AMPM") && !name.equals("CLOCK_HOUR_OF_AMPM")
						&& !name.equals("HOUR_OF_DAY") && !name.equals("CLOCK_HOUR_OF_DAY")
						&& !name.equals("AMPM_OF_DAY") && !name.equals("EPOCH_DAY") && !name.equals("INSTANT_SECONDS")
						&& !name.equals("OFFSET_SECONDS") && !name.equals("PROLEPTIC_MONTH"))
				.collect(Collectors.toList());
	}

	private static Map<String, Integer> getResult(List<String> names, LocalDate now) {

		Function<String, String> keyMapper = (String name) -> String.format("LocalDate.get(ChronoField.%s)", name);

		return names.stream().collect(Collectors.toMap(keyMapper, (String name) -> getValue(name, now)));

	}

	private static Integer getValue(String name, LocalDate now) {

		int value = 0;
		try {
			Method m = now.getClass().getMethod("get", new Class[] { TemporalField.class });
			value = (int) m.invoke(now, new Object[] { ChronoField.valueOf(name) });
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return value;

	}

}
