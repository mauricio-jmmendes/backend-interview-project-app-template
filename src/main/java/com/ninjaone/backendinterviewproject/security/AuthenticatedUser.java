package com.ninjaone.backendinterviewproject.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ninjaone.backendinterviewproject.model.Customer;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthenticatedUser implements UserDetails {

	private final Long id;

	private final String name;

	private final String username;

	private final String email;

	@JsonIgnore
	private final String password;

	private final Collection<? extends GrantedAuthority> authorities;

	public AuthenticatedUser(Long id, String name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static AuthenticatedUser create(Customer customer) {
		List<GrantedAuthority> authorities = customer.getAuthorities().stream()
																								 .map(auth -> new SimpleGrantedAuthority(auth.getType().name()))
																								 .collect(Collectors.toList());

		return new AuthenticatedUser(customer.getId(),
																 customer.getFullName(),
																 customer.getNickname(),
																 customer.getEmail(),
																 customer.getPassword(),
																 authorities);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		AuthenticatedUser that = (AuthenticatedUser) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}
