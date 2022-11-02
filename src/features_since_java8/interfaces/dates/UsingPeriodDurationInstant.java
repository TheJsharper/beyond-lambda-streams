package features_since_java8.interfaces.dates;

import java.time.LocalDate;
import java.time.Period;

public class UsingPeriodDurationInstant {

	public static void main(String[] args) {

		printPeriod();

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

}
