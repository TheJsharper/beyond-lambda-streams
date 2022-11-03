package features_since_java8.interfaces.dates;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class UsingLocalDateAndLocalDateFormatter {

	public static void main(String[] args) {

		parseLocalDate();
		
		parseTime();
		
		formatLocalDate();
		
		formatTime();
		
	}

	private static void parseLocalDate() {
		/**
		 * Using system defined format
		 */
		String date = "2018-04-28";
		LocalDate localDate0 = LocalDate.parse(date);
		System.out.println("localDate0 : " + localDate0);
		LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE); // ISO_DATE is the same
		System.out.println("localDate " + localDate);

		String isoDate = "20180428";
		System.out.println("BASIC_ISO_DATE : " + LocalDate.parse(isoDate, DateTimeFormatter.BASIC_ISO_DATE));

		/**
		 * Custom system defined format
		 */
		String date1 = "2018|04|28";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy|MM|dd");
		LocalDate localDate1 = LocalDate.parse(date1, dateTimeFormatter);
		System.out.println("localDate1 : " + localDate1);

		String date2 = "2018*04*28";
		DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("uuuu*MM*dd");
		LocalDate localDate2 = LocalDate.parse(date2, dateTimeFormatter1);
		System.out.println("localDate2 : " + localDate2);
	}

	private static void formatLocalDate() {
		// DateTimeFormatter dateTimeFormatter =
		// DateTimeFormatter.ofPattern("dd|MM|yyyy");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY|MM|dd");
		LocalDate localDate = LocalDate.now();
		String formattedDate = localDate.format(dateTimeFormatter);
		System.out.println("formattedDate : " + formattedDate);

	}

	private static void parseTime() {

		/**
		 * System defined format
		 */
		String time = "13:00";
		LocalTime localTime = LocalTime.parse(time);
		System.out.println("localTime : " + localTime);

		LocalTime localTime1 = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
		System.out.println("localTime1 : " + localTime1);

		// H -> 24 hour time format
		// h-> 12 hour time format
		String time1 = "13*00";
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH*mm");
		LocalTime localTime2 = LocalTime.parse(time1, dateTimeFormatter);
		System.out.println("localTime2 : " + localTime2);

		String time2 = "13*00*33";
		DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("HH*mm*ss");
		LocalTime localTime3 = LocalTime.parse(time2, dateTimeFormatter1);
		System.out.println("localTime3 : " + localTime3);

	}

	private static void formatTime() {

		LocalTime localTime = LocalTime.of(19, 30);

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		String time = dateTimeFormatter.format(localTime);
		System.out.println("time : " + time);

		LocalTime localTime1 = LocalTime.of(19, 30, 45);
		DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("HH*mm*ss");
		String time1 = dateTimeFormatter1.format(localTime1);
		System.out.println("time1 : " + time1);

	}
}
