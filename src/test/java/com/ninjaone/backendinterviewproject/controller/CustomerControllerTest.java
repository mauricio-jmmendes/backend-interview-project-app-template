package com.ninjaone.backendinterviewproject.controller;

import static com.ninjaone.backendinterviewproject.util.LogInUtils.getTokenForLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.database.AuthorityRepository;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.model.AuthType;
import com.ninjaone.backendinterviewproject.model.Authority;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.util.AbstractRestControllerTest;
import java.util.NoSuchElementException;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class CustomerControllerTest extends AbstractRestControllerTest {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CustomerRepository customerRepository;

	private Customer customer;

	private String token;

	@Autowired
	private AuthorityRepository authorityRepository;

	@BeforeAll()
	public void bootstrap() throws Exception {
		Authority authority = authorityRepository.findByType(AuthType.ADMIN).orElseThrow(NoSuchElementException::new);
		Customer unsavedCustomer = new Customer("046.452.258.69",
																						"Jhon Doe dos Santos",
																						"jhon.doe",
																						"jhon.doe@test.com",
																						new BCryptPasswordEncoder().encode("123456"), Set.of(authority));
		customer = customerRepository.save(unsavedCustomer);
		token = getTokenForLogin("jhon.doe", "123456", getMockMvc());
	}

	@Test
	@Order(1)
	void findById() throws Exception {
		this.mvc.perform(get("/customers/" + customer.getId()).header("Authorization", token))
						.andDo(print())
						.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	void notFoundById() throws Exception {
		this.mvc.perform(get("/customers/10").header("Authorization", token))
						.andDo(print())
						.andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	void findAll() throws Exception {
		mvc.perform(get("/customers").header("Authorization", token))
			 .andDo(print())
			 .andExpect(status().isOk());
	}

	@Test
	@Order(4)
	void updateCustomers() throws Exception {
		this.mvc.perform(put("/customers/" + customer.getId())
												 .header("Authorization", token)
												 .content("{\"fullName\":\"Mauricio Junio Moura Mendes\"}")
												 .contentType("application/json"))
						.andDo(print())
						.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void deleteCustomers() throws Exception {
		this.mvc.perform(delete("/customers/" + customer.getId()).header("Authorization", token))
						.andDo(print())
						.andExpect(status().isNoContent());

		this.mvc.perform(get("/customers/" + customer.getId()))
						.andDo(print())
						.andExpect(status().isNotFound());
	}
}