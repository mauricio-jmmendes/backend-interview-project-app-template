package com.ninjaone.backendinterviewproject.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAutHandler implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(JwtAutHandler.class);

	@Override
	public void commence(HttpServletRequest httpServletRequest,
											 HttpServletResponse httpServletResponse,
											 AuthenticationException e) throws IOException, ServletException {
		logger.error("Unauthorized error. Message - {}", e.getMessage());
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
	}
}
