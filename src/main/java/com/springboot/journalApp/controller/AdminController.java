package com.springboot.journalApp.controller;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

	@Autowired
	private UserService userService;

	@GetMapping("/all-users")
	public ResponseEntity<?> getAllUsers() {
		List<User> all = userService.getAll();
		if (all != null && !all.isEmpty()) {
			return new  ResponseEntity<>(all, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/add-admin-user")
	public void createAdminUser(@RequestBody User user) {
		userService.addAdminUser(user);
	}
}
