package com.springboot.journalApp.service;

import com.springboot.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

	@Value("${weather.api.key}")
	private String apiKey;
	public static final String API = "https://api.weatherstack.com/current?access_key=API&query=city";

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RedisService redisService;

	public WeatherResponse getWeather(String city) {
		WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);

		if(weatherResponse != null) {
			return weatherResponse;
		}
		else {
			String finalAPI = API.replace("API", apiKey).replace("city", city);
			ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
			if (response.getBody() != null) {
				redisService.set("weather_of_" + city, response.getBody(), 3600L);
			}
			return response.getBody();
		}
	}
}