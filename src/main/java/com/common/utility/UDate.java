package com.common.utility;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UDate {

	public static final SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmsss");
	public static final SimpleDateFormat DDMMYY_By_Slash = new SimpleDateFormat("dd/MM/yyyy");
	public static final SimpleDateFormat DDMMYY_By_Minus = new SimpleDateFormat("dd-MMM-yyyy");
	public static final SimpleDateFormat datetime = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	public static final DecimalFormat df = new DecimalFormat("###.###");

	public static Date getDate(String str) {
		return getDate(str, DDMMYY_By_Slash);
	}

	public static Date getDate(String str, SimpleDateFormat sdf) {
		if (Utility.isNullOrEmp(str))
			return null;

		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			throw new UtilityException(e.getMessage());
		}

	}

	public static Date addMinutesToDate(int minutes, Date beforeTime) {
		final long ONE_MINUTE_IN_MILLIS = 60000;// millisecs

		long curTimeInMs = beforeTime.getTime();
		Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
		return afterAddingMins;
	}

	public static Date addToDate(Date date, int unitValue, int unit) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(unit, unitValue);
		return c.getTime();
	}

 

}
