package com.ninjaone.backendinterviewproject.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthFilter extends OncePerRequestFilter {

	private static final Logger LOG = LoggerFactory.getLogger(JwtAuthFilter.class);
	private final JwtTokenService jwtTokenService;
	private final AuthenticationService authenticationService;

	public JwtAuthFilter(JwtTokenService jwtTokenService, AuthenticationService authenticationService) {
		this.jwtTokenService = jwtTokenService;
		this.authenticationService = authenticationService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
			String token = getTokenFrom(request);

			if (jwtTokenService.validateToken(token)) {
				Long userId = jwtTokenService.getId(token);
				UserDetails userDetails = authenticationService.loadUserById(userId);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception ex) {
			LOG.error("Could not set user authentication in security context", ex);
		}

		filterChain.doFilter(request, response);
	}

	private String getTokenFrom(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
			return token.substring(7);
		}
		return token;
	}
}
