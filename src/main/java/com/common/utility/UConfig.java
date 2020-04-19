package com.common.utility;

import org.springframework.web.client.RestTemplate;

public abstract class UConfig {

	public void setRestTemplate(RestTemplate rest) {
		URest.setRestTemplate(rest);
	}
 
}
 