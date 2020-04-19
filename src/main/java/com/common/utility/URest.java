package com.common.utility;
 
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
 
/**
 * @author Rahul Mangal 16-Nov-2019
 */
  
public class URest {

 
	private static RestTemplate restTemplate = null;
 
	static void setRestTemplate(RestTemplate rest) {
		RestTemplate restTemplate = null;
		if (rest == null) {
			restTemplate = new RestTemplate();
		}
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
	}
	
	
	public Object getRequest(String url) {
		return getRequest(url,Object.class);
	}
	
	public <T> T getRequest(String url, Class<T> responseType ) {
		return (T) getRequest(url,responseType,false);
	}
	
	public <T> T getRequest(String url, Class<T> responseType,boolean infolog ) {
		String METHOD = "getRequest()- ";
		if(infolog){}
			////log.info(METHOD + "Started  url " + url);
		ResponseEntity<T> reponse = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		try {
			reponse = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(headers), responseType);
		} catch (ResourceAccessException e) {
			//log.error(METHOD + "ResourceAccessException-  " + e.getClass() + e.getMessage());
			throw new UtilityException(UConstant.BOT_IS_NOT_ACCESSIBLE);
		} catch (HttpClientErrorException e) {
			//log.error(METHOD + " HttpClientErrorException- {} " + e.getMessage());
			if (e != null && e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				UtilityException.throwErr(UConstant.UNAUTHORIZED_ACCESS);
			}
			throw new UtilityException(getErrorMessage(e.getResponseBodyAsString()));
		} catch (RestClientException e) {
			//log.error(METHOD + " RestClientException- ", e);
			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);
		}

		if (reponse == null) {
			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		if (!HttpStatus.OK.equals(reponse.getStatusCode())) {
			throw new UtilityException(getErrorMessage(Utility.getStr(reponse.getBody())));
		}
		
		if(infolog){}
		//log.info(METHOD + " End ---");
		return (T) reponse.getBody();
	}
	
	
	
	/**
	 * @param url
	 * @param body
	 * @return
	 *
	 * 		Created on: 09-Mar-2020 Author: Rahul.Mangal
	 */
	public Object post_JSON_Request(String url, Object body) {
		return postRequest(url, body, Object.class, MediaType.APPLICATION_JSON);
	}

	/**
	 * @param url
	 * @param body
	 * @return
	 */
	public Object post_FORM_DATA_Request(String url, Object body) {
		return postRequest(url, body, Object.class, MediaType.MULTIPART_FORM_DATA);
	}

	/**
	 * @param url
	 * @param body
	 * @return
	 *
	 * 		Created on: 09-Mar-2020 Author: Rahul.Mangal
	 */
	public <T> T post_JSON_Request(String url, Object body, Class<T> responseType) {
		return postRequest(url, body, responseType, MediaType.APPLICATION_JSON);
	}

	/**
	 * @param url
	 * @param body
	 * @return
	 */
	public <T> T post_FORM_DATA_Request(String url, Object body, Class<T> responseType) {
		return postRequest(url, body, responseType, MediaType.MULTIPART_FORM_DATA);
	}

	/**
	 * @param url
	 * @param body
	 * @param responseType
	 * 
	 * @throw NullPointerException if arguments is null.
	 * @throw UtilityException with generic message, where spring rest exception are
	 *        being internally handle during exchange method call.
	 * @return Object of 'responseType' type
	 * 
	 *         Created on: 13-Feb-2020 Author: Rahul.Mangal
	 */
	public <T> T postRequest(String url, Object body, Class<T> responseType, MediaType mediaType) {
		String METHOD = "postRequest()- ";
		//log.info(METHOD + "Started  url " + url);
		ResponseEntity<T> reponse = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);

		try {
			reponse = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(body, headers), responseType);
		} catch (ResourceAccessException e) {
			//log.error(METHOD + "ResourceAccessException-  " + e.getClass() + e.getMessage());
			throw new UtilityException(UConstant.BOT_IS_NOT_ACCESSIBLE);
		} catch (HttpClientErrorException e) {
			//log.error(METHOD + " HttpClientErrorException- {} " + e.getMessage());
			if (e != null && e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				UtilityException.throwErr(UConstant.UNAUTHORIZED_ACCESS);
			}
			throw new UtilityException(getErrorMessage(e.getResponseBodyAsString()));
		} catch (RestClientException e) {
			//log.error(METHOD + " RestClientException- ", e);
			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);
		}

		if (reponse == null) {
			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);
		}
		if (!HttpStatus.OK.equals(reponse.getStatusCode())) {
			throw new UtilityException(getErrorMessage(Utility.getStr(reponse.getBody())));
		}

		//log.info(METHOD + " End ---");
		return (T) reponse.getBody();
	}

	/**
	 * To parse and send proper response string to user in case of exception.
	 * 
	 * @param reponse
	 * @return
	 */
	public static String getErrorMessage(String reponse) {
		////log.info("Requested URL - Resonse - " + reponse);
		if (Utility.isNullOrEmp(reponse)) {
			return UConstant.INTERNAL_SERVER_ERROR_MESSAGE;
		}
		try {
			JsonNode json = UJson.mapper.readTree(reponse);
			JsonNode str = json.get("Message");
			if (str == null)
				str = json.get("message");
			if (str.asText() != null && !str.asText().isEmpty())
				return str.asText();

		} catch (Exception e) {
			////log.error("While reading smart bot reponse " + reponse + "  " + e.getMessage());
		}
		return UConstant.INTERNAL_SERVER_ERROR_MESSAGE;
	}

}
