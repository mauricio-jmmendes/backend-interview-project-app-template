package com.ninjaone.backendinterviewproject.util;

import static com.ninjaone.backendinterviewproject.util.LogInUtils.getTokenForLogin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.ninjaone.backendinterviewproject.database.AuthorityRepository;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.model.AuthType;
import com.ninjaone.backendinterviewproject.model.Authority;
import com.ninjaone.backendinterviewproject.model.Customer;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
@Data
@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractRestControllerTest {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	private Customer customer;

	private String token;

	@BeforeAll()
	@Transactional
	public void setUp() throws Exception {
		SecurityContextHolder.clearContext();
		Authority authority = authorityRepository.findByType(AuthType.ADMIN).orElseThrow(NoSuchElementException::new);
		Customer unsavedCustomer = createRandomCustomer(authority);
		customer = customerRepository.save(unsavedCustomer);
		token = getTokenForLogin(customer.getNickname(), "123456", getMockMvc());
	}

	private Customer createRandomCustomer(Authority authority) {
		Faker faker = new Faker();
		return new Customer(faker.idNumber().ssnValid(),
												faker.name().fullName(),
												faker.name().username(),
												faker.internet().emailAddress(),
												new BCryptPasswordEncoder().encode("123456"),
												Set.of(authority));
	}
}
