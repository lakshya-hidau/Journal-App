package com.springboot.journalApp.controller;

import com.springboot.journalApp.api.response.WeatherResponse;
import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import com.springboot.journalApp.service.UserService;
import com.springboot.journalApp.service.WeatherService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private WeatherService weatherService;

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User userInDb = userService.findByUsername(userName);
		if (userInDb == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userInDb.setUsername(user.getUsername());
		userInDb.setPassword(passwordEncoder.encode(user.getPassword()));
		userService.saveNewEntry(userInDb);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping
	public ResponseEntity<?> deleteUserById() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User userInDb = userService.findByUsername(authentication.getName());
		if (userInDb == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		userRepository.deleteByUsername(authentication.getName());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<?> greeting() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
		String greeting = "";
		if (weatherResponse != null) {
			greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
		}
		return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
	}
}