package com.springboot.journalApp.service;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled
@SpringBootTest
public class EmailServiceTest {

	@Autowired
	private EmailService emailService;

	@Test
	public void testSendEmail() {
		emailService.sendEmail("XXXXXXXXXXXXXXXX@gmail.com",
				"Test Java Mail",
				"Hello, This is a test email from Java Mail");
	}
}
