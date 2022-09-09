package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CustomerControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private CustomerService customerService;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void save() throws Exception {
		mvc.perform(post("/customers")
						.contentType("application/json")
						.content("{\"customerId\":\"04238265185\",\"name\":\"Mauricio Mendes\",\"email\":\"mauricio.jmmendes@gmail.com\"}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void findById() throws Exception {
		mvc.perform(post("/customers")
						.contentType("application/json")
						.content("{\"customerId\":\"04238265185\",\"name\":\"Mauricio Mendes\",\"email\":\"mauricio.jmmendes@gmail.com\"}"))
				.andDo(print())
				.andExpect(status().isOk());

		CustomerDTO customerDTO = customerService.findAll().stream().findFirst().orElseThrow();

		this.mvc.perform(get("/customers/" + customerDTO.getId()))
				.andDo(print())
				.andExpect(status().is(200));
	}

	@Test
	void notFoundById() throws Exception {
		this.mvc.perform(get("/customers/10"))
				.andDo(print())
				.andExpect(status().is(204));
	}


	@Test
	void deleteCustomers() throws Exception {
		mvc.perform(post("/customers/")
						.contentType("application/json")
						.content("{\"customerId\":\"04238265185\",\"name\":\"Mauricio Mendes\",\"email\":\"mauricio.jmmendes@gmail.com\"}"))
				.andDo(print())
				.andExpect(status().isOk());

		this.mvc.perform(delete("/customers/1").contentType("application/json"))
				.andDo(print())
				.andExpect(status().isNoContent());

		this.mvc.perform(get("/customers/1"))
				.andDo(print())
				.andExpect(status().is(204));
	}

	@Test
	void findAll() throws Exception {
		mvc.perform(post("/customers")
						.contentType("application/json")
						.content("{\"customerId\":\"04238265185\",\"name\":\"Mauricio Mendes\",\"email\":\"mauricio.jmmendes@gmail.com\"}"))
				.andDo(print())
				.andExpect(status().isOk());
		mvc.perform(post("/customers")
						.contentType("application/json")
						.content("{\"customerId\":\"03454323498\",\"name\":\"Junio Moura\",\"email\":\"junio.moura@gmail.com\"}"))
				.andDo(print())
				.andExpect(status().isOk());

		mvc.perform(get("/customers"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void updateCustomers() throws Exception {
		mvc.perform(post("/customers")
						.contentType("application/json")
						.content("{\"customerId\":\"04238265185\",\"name\":\"Mauricio Mendes\",\"email\":\"mauricio.jmmendes@gmail.com\"}"))
				.andDo(print())
				.andExpect(status().isOk());

		CustomerDTO customerDTO = customerService.findAll().stream().findFirst().orElseThrow();

		this.mvc.perform(put("/customers/" + customerDTO.getId())
						.content("{\"customerId\":\"04238265185\",\"name\":\"Mauricio Junio Moura Mendes\",\"email\":\"mauricio.jmmendes@gmail.com\"}")
						.contentType("application/json"))
				.andDo(print())
				.andExpect(status().is(200));
	}
}