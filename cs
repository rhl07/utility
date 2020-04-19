warning: LF will be replaced by CRLF in .gitignore.
The file will have its original line endings in your working directory
[1mdiff --git a/.gitignore b/.gitignore[m
[1mindex a1c2a23..c836fce 100644[m
[1m--- a/.gitignore[m
[1m+++ b/.gitignore[m
[36m@@ -21,3 +21,4 @@[m
 [m
 # virtual machine crash logs, see http://www.java.com/en/download/help/error_hotspot.xml[m
 hs_err_pid*[m
[32m+[m[32m/target/[m
[1mdiff --git a/pom.xml b/pom.xml[m
[1mindex 3bf2905..84d4687 100644[m
[1m--- a/pom.xml[m
[1m+++ b/pom.xml[m
[36m@@ -25,6 +25,14 @@[m
 [m
 	<dependencies>[m
 [m
[32m+[m		[32m<dependency>[m
[32m+[m			[32m<groupId>org.slf4j</groupId>[m
[32m+[m			[32m<artifactId>slf4j-api</artifactId>[m
[32m+[m			[32m<version>${org.slf4j-version}</version>[m
[32m+[m			[32m<scope>provided</scope>[m
[32m+[m		[32m</dependency>[m
[32m+[m
[32m+[m
 		<dependency>[m
 			<groupId>org.springframework</groupId>[m
 			<artifactId>spring-webmvc</artifactId>[m
[1mdiff --git a/src/main/java/com/common/utility/URest.java b/src/main/java/com/common/utility/URest.java[m
[1mindex d280d2d..9fea048 100644[m
[1m--- a/src/main/java/com/common/utility/URest.java[m
[1m+++ b/src/main/java/com/common/utility/URest.java[m
[36m@@ -1,5 +1,7 @@[m
 package com.common.utility;[m
  [m
[32m+[m[32mimport org.slf4j.Logger;[m
[32m+[m[32mimport org.slf4j.LoggerFactory;[m
 import org.springframework.http.HttpEntity;[m
 import org.springframework.http.HttpHeaders;[m
 import org.springframework.http.HttpMethod;[m
[36m@@ -23,6 +25,8 @@[m [mimport com.fasterxml.jackson.databind.JsonNode;[m
 public class URest {[m
 [m
  [m
[32m+[m	[32mprivate static final Logger log = LoggerFactory.getLogger(URest.class);[m
[32m+[m[41m	[m
 	private static RestTemplate restTemplate = null;[m
  [m
 	static void setRestTemplate(RestTemplate rest) {[m
[36m@@ -46,8 +50,8 @@[m [mpublic class URest {[m
 	[m
 	public <T> T getRequest(String url, Class<T> responseType,boolean infolog ) {[m
 		String METHOD = "getRequest()- ";[m
[31m-		if(infolog){}[m
[31m-			////log.info(METHOD + "Started  url " + url);[m
[32m+[m		[32mif(infolog)[m
[32m+[m			[32mlog.info(METHOD + "Started  url " + url);[m
 		ResponseEntity<T> reponse = null;[m
 [m
 		HttpHeaders headers = new HttpHeaders();[m
[36m@@ -56,16 +60,16 @@[m [mpublic class URest {[m
 		try {[m
 			reponse = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<Object>(headers), responseType);[m
 		} catch (ResourceAccessException e) {[m
[31m-			//log.error(METHOD + "ResourceAccessException-  " + e.getClass() + e.getMessage());[m
[32m+[m			[32mlog.error(METHOD + "ResourceAccessException-  " + e.getClass() + e.getMessage());[m
 			throw new UtilityException(UConstant.BOT_IS_NOT_ACCESSIBLE);[m
 		} catch (HttpClientErrorException e) {[m
[31m-			//log.error(METHOD + " HttpClientErrorException- {} " + e.getMessage());[m
[32m+[m			[32mlog.error(METHOD + " HttpClientErrorException- {} " + e.getMessage());[m
 			if (e != null && e.getStatusCode() == HttpStatus.UNAUTHORIZED) {[m
 				UtilityException.throwErr(UConstant.UNAUTHORIZED_ACCESS);[m
 			}[m
 			throw new UtilityException(getErrorMessage(e.getResponseBodyAsString()));[m
 		} catch (RestClientException e) {[m
[31m-			//log.error(METHOD + " RestClientException- ", e);[m
[32m+[m			[32mlog.error(METHOD + " RestClientException- ", e);[m
 			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);[m
 		}[m
 [m
[36m@@ -76,8 +80,8 @@[m [mpublic class URest {[m
 			throw new UtilityException(getErrorMessage(Utility.getStr(reponse.getBody())));[m
 		}[m
 		[m
[31m-		if(infolog){}[m
[31m-		//log.info(METHOD + " End ---");[m
[32m+[m		[32mif(infolog)[m
[32m+[m			[32mlog.info(METHOD + " End ---");[m
 		return (T) reponse.getBody();[m
 	}[m
 	[m
[36m@@ -137,7 +141,7 @@[m [mpublic class URest {[m
 	 */[m
 	public <T> T postRequest(String url, Object body, Class<T> responseType, MediaType mediaType) {[m
 		String METHOD = "postRequest()- ";[m
[31m-		//log.info(METHOD + "Started  url " + url);[m
[32m+[m		[32mlog.info(METHOD + "Started  url " + url);[m
 		ResponseEntity<T> reponse = null;[m
 [m
 		HttpHeaders headers = new HttpHeaders();[m
[36m@@ -146,16 +150,16 @@[m [mpublic class URest {[m
 		try {[m
 			reponse = restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<Object>(body, headers), responseType);[m
 		} catch (ResourceAccessException e) {[m
[31m-			//log.error(METHOD + "ResourceAccessException-  " + e.getClass() + e.getMessage());[m
[32m+[m			[32mlog.error(METHOD + "ResourceAccessException-  " + e.getClass() + e.getMessage());[m
 			throw new UtilityException(UConstant.BOT_IS_NOT_ACCESSIBLE);[m
 		} catch (HttpClientErrorException e) {[m
[31m-			//log.error(METHOD + " HttpClientErrorException- {} " + e.getMessage());[m
[32m+[m			[32mlog.error(METHOD + " HttpClientErrorException- {} " + e.getMessage());[m
 			if (e != null && e.getStatusCode() == HttpStatus.UNAUTHORIZED) {[m
 				UtilityException.throwErr(UConstant.UNAUTHORIZED_ACCESS);[m
 			}[m
 			throw new UtilityException(getErrorMessage(e.getResponseBodyAsString()));[m
 		} catch (RestClientException e) {[m
[31m-			//log.error(METHOD + " RestClientException- ", e);[m
[32m+[m			[32mlog.error(METHOD + " RestClientException- ", e);[m
 			throw new UtilityException(UConstant.INTERNAL_SERVER_ERROR_MESSAGE);[m
 		}[m
 [m
[36m@@ -166,7 +170,7 @@[m [mpublic class URest {[m
 			throw new UtilityException(getErrorMessage(Utility.getStr(reponse.getBody())));[m
 		}[m
 [m
[31m-		//log.info(METHOD + " End ---");[m
[32m+[m		[32mlog.info(METHOD + " End ---");[m
 		return (T) reponse.getBody();[m
 	}[m
 [m
[36m@@ -177,7 +181,7 @@[m [mpublic class URest {[m
 	 * @return[m
 	 */[m
 	public static String getErrorMessage(String reponse) {[m
[31m-		////log.info("Requested URL - Resonse - " + reponse);[m
[32m+[m		[32mlog.info("Requested URL - Resonse - " + reponse);[m
 		if (Utility.isNullOrEmp(reponse)) {[m
 			return UConstant.INTERNAL_SERVER_ERROR_MESSAGE;[m
 		}[m
[36m@@ -190,7 +194,7 @@[m [mpublic class URest {[m
 				return str.asText();[m
 [m
 		} catch (Exception e) {[m
[31m-			////log.error("While reading smart bot reponse " + reponse + "  " + e.getMessage());[m
[32m+[m			[32mlog.error("While reading smart bot reponse " + reponse + "  " + e.getMessage());[m
 		}[m
 		return UConstant.INTERNAL_SERVER_ERROR_MESSAGE;[m
 	}[m
