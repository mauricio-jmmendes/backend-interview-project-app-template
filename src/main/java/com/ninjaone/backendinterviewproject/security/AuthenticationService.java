package com.ninjaone.backendinterviewproject.security;

import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.Customer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthenticationService implements UserDetailsService {

	private final CustomerRepository customerRepository;

	public AuthenticationService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String nicknameOrEmail) throws UsernameNotFoundException {
		Customer customer;

		if (nicknameOrEmail.contains("@")) {
			customer = customerRepository.findByEmail(nicknameOrEmail).orElseThrow(
					() -> new UsernameNotFoundException(String.format("Customer not found with provided email: %s", nicknameOrEmail)));
		} else {
			customer = customerRepository.findByNickname(nicknameOrEmail).orElseThrow(
					() -> new UsernameNotFoundException(String.format("Customer not found with provided nickname: %s", nicknameOrEmail)));
		}

		return AuthenticatedUser.create(customer);
	}

	@Transactional
	public UserDetails loadUserById(Long id) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
		return AuthenticatedUser.create(customer);
	}
}