package com.springboot.journalApp.service;


import com.springboot.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Disabled
public class UserServiceTest {

	@Autowired
	private UserRepository userRepository;

	@ParameterizedTest
	@CsvSource({
			"user"
	})
	public void testFindUserByUsername(String name) {
		assertNotNull(userRepository.findByUsername(name));
	}
}
