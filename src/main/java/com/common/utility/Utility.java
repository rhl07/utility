package com.common.utility;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class Utility {

	public static synchronized String getUniqString() {
		Date date = new Date();
		return UDate.sf.format(date);
	}

	public static boolean isYesOrNo(String str) {

		if ("Yes".equals(str) || "No".equals(str))
			return true;
		return false;
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isNullOrEmp(String str) {
		if (str == null || str.trim().isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * Convert Object to String if Object is not null, else return null.
	 * 
	 * @param obj
	 * @return
	 */
	public static String getStr(Object obj) {
		if (obj != null) {
			return obj.toString();
		}
		return null;
	}

	/**
	 * @param str
	 * @return
	 */
	public static String getValue(String str, String key) {
		if (str == null || str.trim().isEmpty())
			throw new UtilityException(key + " is Required");
		else
			return str.trim();
	}

	/**
	 * @param str
	 * @param def
	 * @return default given 'def' value if str is empty or null
	 */
	public static String defOnEmp(String str, String def) {
		if (str == null || str.trim().isEmpty())
			return def;
		else
			return str;
	}

	public static String shortStr(String str) {
		if (!isNullOrEmp(str) && str.length() > 20) {
			return str.substring(0, 19) + ".....";
		}
		return str;
	}

	public static String getExtension(String filename) {
		filename = getValue(filename, "file name");
		String ext = filename.substring(filename.lastIndexOf("."), filename.length());
		return ext;
	}

	public static String getTempFileLocation() {
		String str = System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + "nlp_file"
				+ System.getProperty("file.separator");
		File file = new File(str);
		if (!file.exists()) {
			file.mkdir();
		}
		return str;
	}

	public static Long getLong(String str) {
		if (!isNullOrEmp(str)) {
			return Long.parseLong(str);
		}
		return null;
	}

	public static Integer getInt(String str) {
		if (!isNullOrEmp(str)) {
			return Integer.parseInt(str);
		}
		return null;
	}

	public static Double getDouble(String str) {
		if (!isNullOrEmp(str)) {
			return Double.parseDouble(str);
		}
		return null;
	}

	public static Double getDouble(String str, String message) {
		if (!isNullOrEmp(str)) {
			return Double.parseDouble(str);
		} else if (!isNullOrEmp(message)) {
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

	public static void safeClose(InputStream inputStream, OutputStream outputStream) {
		safeCloseIStream(inputStream);
		safeCloseOStream(outputStream);
	}

	public static void safeCloseIStream(InputStream inputStream) {

		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public static void safeCloseOStream(OutputStream outputStream) {

		if (outputStream != null) {
			try {
				outputStream.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
