package com.springboot.journalApp.service;

import com.springboot.journalApp.entity.User;
import com.springboot.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder; // Injected PasswordEncoder bean

	// Save new user (register user with password encoding)
	public void saveNewEntry(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword); // Ensure password is encoded
		user.setRoles(Arrays.asList("USER")); // Assign default role(s)
		userRepository.save(user); // Save the user with encoded password
	}

	// Save user after modification
	public void saveEntry(User user) {
		userRepository.save(user);
	}

	public void addAdminUser(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword); // Ensure password is encoded
		user.setRoles(Arrays.asList("USER", "ADMIN"));
		userRepository.save(user); // Save the user with encoded password
	}

	// Get all users
	public List<User> getAll() {
		return userRepository.findAll();
	}

	// Find user by username
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	// Delete user by username
	public void deleteUserByUsername(String username) {
		userRepository.deleteByUsername(username);
	}
}
