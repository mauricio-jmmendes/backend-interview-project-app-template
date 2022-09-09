package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.controller.domain.ApiResponse;
import com.ninjaone.backendinterviewproject.controller.domain.LoginRequest;
import com.ninjaone.backendinterviewproject.controller.domain.SignUpRequest;
import com.ninjaone.backendinterviewproject.database.AuthorityRepository;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.exception.InvalidRequestException;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.AuthType;
import com.ninjaone.backendinterviewproject.model.Authority;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.security.AuthResponse;
import com.ninjaone.backendinterviewproject.security.JwtTokenService;
import java.net.URI;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	final AuthenticationManager authenticationManager;

	final CustomerRepository customerRepository;

	final AuthorityRepository authorityRepository;

	final PasswordEncoder passwordEncoder;

	final JwtTokenService tokenProvider;

	public AuthController(AuthenticationManager authenticationManager,
												CustomerRepository customerRepository,
												AuthorityRepository authorityRepository,
												PasswordEncoder passwordEncoder,
												JwtTokenService tokenProvider) {
		this.authenticationManager = authenticationManager;
		this.customerRepository = customerRepository;
		this.authorityRepository = authorityRepository;
		this.passwordEncoder = passwordEncoder;
		this.tokenProvider = tokenProvider;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> loginCustomer(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword())
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = tokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new AuthResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUpCustomer(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult errResult) {
		if (errResult.hasErrors()) {
			throw new InvalidRequestException(errResult);
		}

		if (customerRepository.existsByNickname(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ApiResponse(false, "Username already exists!"), HttpStatus.BAD_REQUEST);
		}

		if (customerRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ApiResponse(false, "Email address already exists!"), HttpStatus.BAD_REQUEST);
		}

		Customer customer = new Customer(signUpRequest.getDocumentId(),
																		 signUpRequest.getName(),
																		 signUpRequest.getUsername(),
																		 signUpRequest.getEmail(),
																		 signUpRequest.getPassword());

		customer.setPassword(passwordEncoder.encode(customer.getPassword()));
		Authority authority = authorityRepository.findByType(AuthType.USER)
																						 .orElseThrow(() -> new ResourceNotFoundException("Authority", AuthType.USER.name(), "Customer Authority not set."));

		customer.setAuthorities(Collections.singleton(authority));
		Customer savedCustomer = customerRepository.save(customer);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/customers/{id}").buildAndExpand(savedCustomer.getId()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "Customer registered successfully"));
	}
}
