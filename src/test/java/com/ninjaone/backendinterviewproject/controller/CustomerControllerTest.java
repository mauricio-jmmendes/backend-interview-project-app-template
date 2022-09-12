package com.ninjaone.backendinterviewproject.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ninjaone.backendinterviewproject.util.AbstractRestControllerTest;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class CustomerControllerTest extends AbstractRestControllerTest {

	@Test
	@Order(1)
	void findById() throws Exception {
		this.getMvc().perform(get("/customers/" + this.getCustomer().getId()).header("Authorization", this.getToken()))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	void notFoundById() throws Exception {
		this.getMvc().perform(get("/customers/10").header("Authorization", this.getToken()))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	void findAll() throws Exception {
		this.getMvc().perform(get("/customers").header("Authorization", this.getToken()))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(4)
	void updateCustomers() throws Exception {
		this.getMvc().perform(put("/customers/" + this.getCustomer().getId())
															.header("Authorization", this.getToken())
															.content("{\"fullName\":\"Mauricio Junio Moura Mendes\"}")
															.contentType("application/json"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void deleteCustomers() throws Exception {
		this.getMvc().perform(delete("/customers/" + this.getCustomer().getId()).header("Authorization", this.getToken()))
				.andDo(print())
				.andExpect(status().isNoContent());

		this.getMvc().perform(get("/customers/" + this.getCustomer().getId()))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}