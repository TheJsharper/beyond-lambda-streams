package features_since_java8.date;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.stream.Collectors;

public class UsingPeriodDurationInstant {

	public static void main(String[] args) {

		printPeriod();

		printComparingTime();

		printInstant();

	}

	private static void printPeriod() {
		LocalDate now = LocalDate.now();
		LocalDate ofY2K = LocalDate.of(2000, 12, 31);
		Period until = ofY2K.until(now);

		System.out.printf("Period ToString %s\n", until);
		System.out.printf("Period Days: %s Months: %s Years %s from (Y)ear (2K)ilo computer problem\n", until.getDays(),
				until.getMonths(), until.getYears());

		Period between = Period.between(ofY2K, now);

		System.out.println("getDays : " + between.getDays());

		System.out.println("getMonths : " + between.getMonths());

		System.out.println("getYears : " + between.getYears());

	}

	private static void printComparingTime() {

		LocalTime localTime = LocalTime.of(7, 20);

		LocalTime localTime1 = LocalTime.of(8, 20);

		long minutesDiff = localTime.until(localTime1, ChronoUnit.MINUTES); // the end time is always exclusive
		System.out.println("until Minutes Diff : " + minutesDiff);

		Duration duration = Duration.between(localTime, localTime1);
		System.out.println("duration  : " + duration.toMinutes());

		Duration duration1 = Duration.ofHours(3);
		System.out.println(duration1.toMinutes());

		Duration duration2 = Duration.ofMinutes(3);
		System.out.println(duration2.toMinutes());

		/**
		 * LocalDateTime
		 */
		LocalDateTime localDateTime = LocalDateTime.now();
		LocalDateTime localDateTime1 = LocalDateTime.of(2018, 8, 13, 23, 59);

		Duration duration3 = Duration.between(localDateTime, localDateTime1);

		System.out.println(duration3.toDays());

		/**
		 * LocalDate - not supported
		 */

		try {
			LocalDate localDate = LocalDate.now();
			LocalDate localDate1 = LocalDate.of(2018, 8, 13);

			Duration duration4 = Duration.between(localDate, localDate1);
			System.out.println(duration4.toDays());
		} catch (UnsupportedTemporalTypeException e) {
			System.err.printf("NOT SUPPORT OPERATION %s\n", e.getMessage());
		}

	}

	private static void printInstant() {

		Instant now = Instant.now();
		System.out.printf("Instant now %s\n", now);
		long epochSecond = now.getEpochSecond();
		int nano = now.getNano();
		Instant ofNow0EpochMilli = Instant.ofEpochMilli(0);

		Instant ofNowEpochMilli = Instant.ofEpochMilli(LocalDateTime.now().toEpochSecond(ZoneOffset.MIN));

		System.out.printf(
				"EpochSecond from Jan. 1st 1970 until now: %d\nNano: %s\nEpochMilisecond offset 0: %s\nEpochMilisecond offset now: %s\n",
				epochSecond, nano, ofNow0EpochMilli, ofNowEpochMilli);

		Instant nowLater = Instant.now();

		Duration duration = Duration.between(now, nowLater);
		var units = duration.getUnits().stream().map((unit) -> unit.toString()).collect(Collectors.joining(";"));
		System.out.printf("Nano : %d Second: %d Miliseconds: %d Units: %s", duration.getNano(), duration.getSeconds(),
				duration.toMillis(), units);
	}

}
