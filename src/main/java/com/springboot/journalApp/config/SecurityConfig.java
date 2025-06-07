package com.springboot.journalApp.config;

import com.springboot.journalApp.filter.JwtFilter;
import com.springboot.journalApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService; // Inject the custom UserDetailsService

	@Autowired
	private JwtFilter jwtFilter; // Inject the JWT filter

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// HTTP security configuration
		http.csrf().disable() // Disable CSRF for simplicity
				.authorizeRequests()
				.antMatchers("/public/**", "/admin/add-admin-user").permitAll() // Public endpoints
				.antMatchers("/journal/**", "/user/**").authenticated() // Secured endpoints
				.antMatchers("/admin/**").hasRole("ADMIN"); // Any other request can be accessed by anyone

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT filter before UsernamePasswordAuthenticationFilter

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Configure authentication to use the custom UserDetailsService and PasswordEncoder
		auth.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder()); // Use the password encoder
	}

	// Define PasswordEncoder bean to handle password encoding and matching
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // BCrypt encoding for passwords
	}

	// Expose AuthenticationManager as a Bean (needed for other authentication flows)
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
