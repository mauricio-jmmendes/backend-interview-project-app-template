package com.ninjaone.backendinterviewproject.util;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.security.AuthResponse;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

public final class LogInUtils {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	private LogInUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String signUpCustomer(String username, String password, MockMvc mockMvc) throws Exception {
		String content = mockMvc.perform(post("/api/auth/signin")
																				 .contentType(MediaType.APPLICATION_JSON)
																				 .content(String.format("{\"password\": \"%s\", \"username\": \"%s\"}", password, username)))
														.andReturn()
														.getResponse()
														.getContentAsString();
		AuthResponse authResponse = OBJECT_MAPPER.readValue(content, AuthResponse.class);

		return authResponse.getAccessToken();
	}

	public static String getTokenForLogin(String username, String password, MockMvc mockMvc) throws Exception {
		String content = mockMvc.perform(post("/api/auth/signin")
																				 .contentType(MediaType.APPLICATION_JSON)
																				 .content(String.format("{\"usernameOrEmail\": \"%s\", \"password\": \"%s\"}", username, password)))
														.andReturn()
														.getResponse()
														.getContentAsString();
		AuthResponse authResponse = OBJECT_MAPPER.readValue(content, AuthResponse.class);

		return authResponse.getAccessToken();
	}
}
