package features_since_java8.dates;

import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class UsingLocalTime {

	public static void main(String[] args) {
		printInstance();

		printLocalTimeValues();

		printModifyingVales();
		
		printComparator();
	}

	private static void printInstance() {

		LocalTime now = LocalTime.now();

		LocalTime twentyThreeOClock = LocalTime.of(23, 33);

		LocalTime twentyTwoOClock = LocalTime.of(22, 33, 45);

		System.out.printf("Instance by LocalTime.now(): %s\n", now);

		System.out.printf("Instance by LocalTime.of(23, 33): %s\n", twentyThreeOClock);

		System.out.printf("Instance by LocalTime.of(22, 33, 45): %s\n", twentyTwoOClock);
	}

	private static void printLocalTimeValues() {

		LocalTime now = LocalTime.now();

		System.out.printf("Instance Hour: %s Minutes: %s Second: %s Nano: %s\n", now.getHour(), now.getMinute(),
				now.getSecond(), now.getNano());

		System.out.printf("Using Get :%s\n", now.get(ChronoField.CLOCK_HOUR_OF_DAY));

		System.out.printf("Using Get :%s\n", now.get(ChronoField.CLOCK_HOUR_OF_AMPM));

		System.out.printf("toSecondOfDay : %s\n", now.toSecondOfDay());

		System.out.printf("toNanoOfDay : %s\n", now.toNanoOfDay());

	}

	private static void printModifyingVales() {

		LocalTime now = LocalTime.now();

		System.out.println("modify hours : " + now.minusHours(2));

		System.out.println("modify hours : " + now.plusHours(2));

		System.out.println("modify hours using chronoUnit: " + now.minus(2, ChronoUnit.HOURS));

		System.out.println("modify minutes using chronoUnit: " + now.minus(2, ChronoUnit.MINUTES));

		System.out.println("modify using with : " + now.with(LocalTime.MIDNIGHT) + " " + now.with(LocalTime.NOON) + " "
				+ now.with(LocalTime.MAX) + " " + now.with(LocalTime.MIN));

		System.out.println("modify using with chrono field: " + now.with(ChronoField.HOUR_OF_DAY, 22));

		System.out.println("modify hours : " + now.plus(2, ChronoUnit.MINUTES));

		System.out.println("modify withHour : " + now.withHour(2));

		System.out.printf("ChronoField.MICRO_OF_DAY: %s\n ", now.getLong(ChronoField.MICRO_OF_DAY));

		System.out.printf("ChronoField.MICRO_OF_DAY: %s\n", now.getLong(ChronoField.MICRO_OF_SECOND));

	}

	private static void printComparator() {

		LocalTime now = LocalTime.now();

		LocalTime twentyThree = LocalTime.of(23, 30);

		LocalTime twentyThree2 = LocalTime.of(23, 30);

		LocalTime after = LocalTime.of(23, 33, 55);

		System.out.printf("is After: %s to %s: %s\n", now, twentyThree, now.isAfter(twentyThree));
		
		System.out.printf("is After: %s to %s: %s\n", twentyThree, after, after.isAfter(twentyThree));
		
		System.out.printf("is Before: %s to %s: %s\n", now, twentyThree2, now.isBefore(twentyThree2));
		
		System.out.printf("is Before: %s to %s: %s\n", now, twentyThree, now.isBefore(twentyThree));
		
		System.out.printf("is equals: %s to %s: %s\n", twentyThree2, twentyThree, twentyThree2.equals(twentyThree));

	}

}
