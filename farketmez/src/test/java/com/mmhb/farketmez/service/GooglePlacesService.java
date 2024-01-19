package com.mmhb.farketmez.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mmhb.farketmez.config.GoogleMapsProperties;

@Service
public class GooglePlacesService {

	private final GoogleMapsProperties googleMapsProperties;
	private RestTemplate restTemplate;

	@Autowired
	public GooglePlacesService(GoogleMapsProperties googleMapsProperties) {
		this.googleMapsProperties = googleMapsProperties;
		this.restTemplate = new RestTemplate();
	}

	public byte[] getPlacePhoto(String photoReference) throws IOException {
		String baseUrl = "https://maps.googleapis.com/maps/api/place/photo";
		String apiKey = googleMapsProperties.getApiKey();
		String url = String.format("%s?maxwidth=400&photoreference=%s&key=%s", baseUrl, photoReference, apiKey);

		ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);

		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		} else {
			throw new IOException("Failed to download photo from Google Places API");
		}
	}

	// ... diğer metodlar ...
}
