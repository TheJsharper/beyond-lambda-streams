package features_since_java8.interfaces.dates;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class UsingLocalDate {

	public static void main(String[] args) {

		printAllnewClazzes();

		printLocalDateIntances();

		printLocalDateMethods();

		printModifyingValueImmutableLocalDate();

		printComparableDate();
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

	private static void printModifyingValueImmutableLocalDate() {

		LocalDate now = LocalDate.now();

		System.out.printf("Before Plus Weeks: %s  After Plus Weeks: %s\n ", now.get(ChronoField.ALIGNED_WEEK_OF_MONTH),
				now.plusWeeks(1).get(ChronoField.ALIGNED_WEEK_OF_MONTH));

		System.out.printf("Before Plus Years: %s  After Plus Years: %s\n ", now, now.plusYears(1));

		System.out.printf("Before Minus Years: %s  After Minus Years: %s\n ", now, now.minusYears(1));

		System.out.printf("Before Plus Days: %s  After Plus Days: %s\n ", now, now.plusDays(1));

		System.out.printf("Before Minus Days: %s  After Minus Days: %s\n ", now, now.minusDays(1));

		System.out.printf("Before Plus Months: %s  After Plus Months: %s\n ", now, now.plusMonths(1));

		System.out.printf("Before Minus Months: %s  After Minus Months: %s\n ", now, now.minusMonths(1));

		System.out.printf("Before set Years: %s  After set Years: %s\n ", now, now.withYear(1995));

		System.out.printf("Before set Months: %s  After set Months: %s\n ", now, now.withMonth(5));

		System.out.printf("Before set Day of Days: %s  After set Day of Year: %s\n ", now, now.withDayOfYear(15));

		System.out.printf("Before with(ChronoField.YEAR, 2019): %s  After with(ChronoField.YEAR, 2019): %s\n ", now,
				now.with(ChronoField.YEAR, 2019));

		System.out.printf(
				"Before with(TemporalAdjusters.firstDayOfMonth): %s  with(TemporalAdjusters.firstDayOfMonth): %s\n ",
				now, now.with(TemporalAdjusters.firstDayOfMonth()));

		System.out.printf(
				"Before with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.FRIDAY)): %s  After with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.FRIDAY)): %s\n ",
				now, now.with(TemporalAdjusters.dayOfWeekInMonth(1, DayOfWeek.FRIDAY)));

		System.out.printf("now.isSupported(ChronoUnit.DAYS)? %s  \n ", now.isSupported(ChronoUnit.DAYS));

	}

	private static void printComparableDate() {

		LocalDate now = LocalDate.now();

		LocalDate beforeNow = LocalDate.of(2022, 10, 31);

		LocalDate afternow = LocalDate.of(2022, 12, 15);

		System.out.printf("Is this year LeafYear?: %s\n", now.isLeapYear());

		System.out.printf("Is this year 2000/01/31 LeafYear?: %s\n", LocalDate.of(2000, 01, 31).isLeapYear());

		System.out.printf("Is %s  After %s ?: %s\n ", now, afternow, now.isAfter(afternow));
		
		System.out.printf("Is %s  After %s ?: %s\n ", afternow, now, afternow.isAfter(now));
		
		System.out.printf("Is %s  Before %s ?: %s\n ", now, beforeNow, now.isAfter(beforeNow));
		
		System.out.printf("Is %s  Before %s ?: %s\n ", beforeNow, now, beforeNow.isAfter(now));

	}

}
