package com.mmhb.farketmez.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mmhb.farketmez.config.GoogleMapsProperties;

@Service
public class MapsService {

	private final GoogleMapsProperties googleMapsProperties;
	private final RestTemplate restTemplate;

	@Autowired
	public MapsService(GoogleMapsProperties googleMapsProperties, RestTemplateBuilder restTemplateBuilder) {
		this.googleMapsProperties = googleMapsProperties;
		this.restTemplate = restTemplateBuilder.build();
	}

	public String getPhotoUrlForLocation(String locationQuery) throws Exception {
		// Yer detayları için Google Places API'sini çağır
		String placeDetailsUrl = String.format(
				"https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=%s&inputtype=textquery&fields=photos&key=%s",
				URLEncoder.encode(locationQuery, StandardCharsets.UTF_8.toString()), googleMapsProperties.getApiKey());

		ResponseEntity<String> response = restTemplate.getForEntity(placeDetailsUrl, String.class);

		// JSON cevabını ayrıştırarak fotoğraf referansını elde et
		String photoReference = parsePhotoReferenceFromResponse(response.getBody());

		// Fotoğraf URL'sini almak için Google Places Photo API'sini çağır
		String photoUrl = String.format(
				"https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=%s&key=%s",
				photoReference, googleMapsProperties.getApiKey());

		return photoUrl;
	}

	private String parsePhotoReferenceFromResponse(String response) throws Exception {
		// JSON cevabını işle ve photoReference değerini döndür
		JSONObject jsonResponse = new JSONObject(response);
		JSONArray candidates = jsonResponse.getJSONArray("candidates");
		if (candidates.length() == 0) {
			throw new Exception("No candidates found in the Google Places API response.");
		}
		JSONObject firstCandidate = candidates.getJSONObject(0);
		JSONArray photos = firstCandidate.getJSONArray("photos");
		if (photos.length() == 0) {
			throw new Exception("No photos found for the location.");
		}
		return photos.getJSONObject(0).getString("photo_reference");
	}
}