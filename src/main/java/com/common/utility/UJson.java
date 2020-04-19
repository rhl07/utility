package com.common.utility;

import java.io.IOException;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UJson {

	public static final Logger log = Logger.getLogger("JsonUtils");

	public static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * @param str
	 * @throws NullPointerException
	 *             if argument is null.
	 * @throws RPAException
	 *             RuntimeExecption if Object mapper is not able to parse
	 *             String.
	 * @return
	 */
	public static JsonNode getJson(String str) {
		try {
			return mapper.readTree(str);
		} catch (IOException e) {

			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);
		} catch (Exception e) {

			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE, e);
		}
	}

	public static JsonNode getJson(String str, String message) {
		try {
			return mapper.readTree(str);
		} catch (IOException e) {

			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);
		} catch (Exception e) {

			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE, e);
		}
	}

	/**
	 * @param obj
	 * @throws NullPointerException
	 *             if argument is null.
	 * @throws RPAException
	 *             RuntimeExecption if Object mapper is not able to parse
	 *             String.
	 * @return
	 * 
	 */
	public static String getJsonStr(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {

			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE, e);
		}
	}

}