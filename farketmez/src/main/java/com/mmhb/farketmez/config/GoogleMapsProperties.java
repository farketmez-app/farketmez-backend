package com.mmhb.farketmez.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google.maps")
public class GoogleMapsProperties {

	private String apiKey;

	// Standard getters and setters
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	// toString metodu, objenin temsilini yazdırmak için kullanışlı olabilir
	@Override
	public String toString() {
		return "GoogleMapsProperties{" + "apiKey='" + apiKey + '\'' + '}';
	}
}
