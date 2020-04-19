package com.common.utility;

import java.text.DecimalFormat;
 

public class UNumeric {

	public static final DecimalFormat decimalFormatter = new DecimalFormat(".######");
	public static final DecimalFormat decimalFormatter_2Point = new DecimalFormat(".##");
	
	
	public static Long getLong(String str) {
		if (!Utility.isNullOrEmp(str)) {
			return Long.parseLong(str);
		}
		return null;
	}

	public static Integer getInt(String str) {
		if (!Utility.isNullOrEmp(str)) {
			return Integer.parseInt(str);
		}
		return null;
	}
	
	public static Integer getInt(String str, int defaultValue){
		Integer ii  = getInt(str);
		if(ii==null)
			return defaultValue;
		return ii;
	}

	public static Double getDouble(String str) {
		if (!Utility.isNullOrEmp(str)) {
			return Double.parseDouble(str);
		}
		return null;
	}

	public static Double getDouble(String str, String message) {
		if (!Utility.isNullOrEmp(str)) {
			return Double.parseDouble(str);
		} else if (!Utility.isNullOrEmp(message)) {
			throw new UtilityException(message);
		}
		return null;
	}

	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String getAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}

	public static double roundOf(Double value) {
		return roundOf(value, 1000);
	}

	public static double roundOf(Double value, Integer val) {

		double rounded = Math.round(value * val);
		return rounded / val;
	}

}
