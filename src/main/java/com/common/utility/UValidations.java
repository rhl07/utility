package com.common.utility;

public class UValidations {

	public static String string(String str) {
		return string(str, null);
	}

	public static String string(String str, String message) {

		if (Utility.isNullOrEmp(str)) {
			if (Utility.isNullOrEmp(message)) {
				throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);
			} else {
				throw new UtilityException(message);
			}
		}
		return str;
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
	 * @return
	 */
	public static String getValue(String str, String key, Integer length) {
		if (str == null || str.trim().isEmpty())
			throw new UtilityException(key + " is Required");
		else if (length != null && str.trim().length() > length) {
			throw new UtilityException("Max length " + length + " is allowed to " + key);
		}
		return str.trim();
	}

}
