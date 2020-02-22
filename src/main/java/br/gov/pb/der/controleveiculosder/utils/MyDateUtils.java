package br.gov.pb.der.controleveiculosder.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtils {

	public static final String PATTERN_MAIN = "MMMM 'de' yyyy";
	public static final String PATTERN_FORMAL = "dd/MM/yyyy 'às' hh:mm";
	public static final String PATTERN_DATA = "dd/MM/yyyy";

	
	public static Integer getCurrentYear() {
		LocalDate now = LocalDate.now();
		return now.getYear();
	}
	
	public static Calendar toCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static String formatCalendarTimezone(Calendar cal, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		dateFormat.setTimeZone(cal.getTimeZone());
		return dateFormat.format(cal.getTime());

	}

	public static String calendarToDateString(Calendar calendardate) {
		String strdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if (calendardate != null) {
			strdate = sdf.format(calendardate.getTime());
		}
		return strdate;
	}

	public static String calendarToString(Calendar calendardate) {
		String strdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		if (calendardate != null) {
			strdate = sdf.format(calendardate.getTime());
		}
		return strdate;
	}

	public static String calendarTimestampToString(Calendar calendardate) {
		String strdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		if (calendardate != null) {
			strdate = sdf.format(calendardate.getTime());
		}
		return strdate;
	}

	public static String calendarToStringForPrettyDate(Calendar calendardate) {
		String strdate = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyyTHH:mmZ");

		if (calendardate != null) {
			strdate = sdf.format(calendardate.getTime());
		}
		return strdate;
	}

	public static Date formatCalendar(Calendar cal) {
		Date date = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String date1 = format1.format(date);
		Date inActiveDate = null;
		try {
			inActiveDate = format1.parse(date1);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return inActiveDate;
	}

}
