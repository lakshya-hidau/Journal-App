package com.springboot.journalApp.repository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class UserRepositoryImplTest {

	@Autowired
	private UserRepositoryImpl userRepositoryImpl;

	@Test
	public void testGetUserForSentimentAnalysis() {
		userRepositoryImpl.getUserForSentimentAnalysis();
	}
}