package com.ninjaone.backendinterviewproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ninjaone.backendinterviewproject.service.DeviceService;
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
class DeviceControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private DeviceService deviceService;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void save() throws Exception {
		mvc.perform(post("/devices")
						.contentType("application/json")
						.content("{\"type\":\"WINDOWS_SERVER\",\"systemName\":\"Windows OS\"}"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void findById() throws Exception {
		mvc.perform(post("/devices")
						.contentType("application/json")
						.content("{\"type\":\"WINDOWS_SERVER\",\"systemName\":\"Windows OS\"}"))
				.andDo(print())
				.andExpect(status().isOk());

		this.mvc.perform(get("/devices/1"))
				.andDo(print())
				.andExpect(status().is(200));
	}

	@Test
	void notFoundById() throws Exception {
		this.mvc.perform(get("/devices/10"))
				.andDo(print())
				.andExpect(status().is(204));
	}


	@Test
	void findAll() throws Exception {
		mvc.perform(post("/devices")
						.contentType("application/json")
						.content("{\"type\":\"WINDOWS_SERVER\",\"systemName\":\"Windows OS\"}"))
				.andDo(print())
				.andExpect(status().isOk());
		mvc.perform(post("/devices")
						.contentType("application/json")
						.content("{\"type\":\"WINDOWS_WORKSTATION\",\"systemName\":\"Windows OS\"}"))
				.andDo(print())
				.andExpect(status().isOk());

		mvc.perform(get("/devices"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	public void updateDevices() throws Exception {
		mvc.perform(post("/devices")
						.contentType("application/json")
						.content("{\"type\":\"WINDOWS_SERVER\",\"systemName\":\"Windows OS\"}"))
				.andDo(print())
				.andExpect(status().isOk());

		this.mvc.perform(put("/devices/1")
						.content("{\"type\":\"WINDOWS_SERVER\",\"systemName\":\"Windows Server OS\"}")
						.contentType("application/json"))
				.andDo(print())
				.andExpect(status().is(200));
	}

	@Test
	public void deleteDevices() throws Exception {
		mvc.perform(post("/devices/")
						.contentType("application/json")
						.content("{\"type\":\"WINDOWS_SERVER\",\"systemName\":\"Windows OS\"}"))
				.andDo(print())
				.andExpect(status().isOk());

		this.mvc.perform(delete("/devices/1").contentType("application/json"))
				.andDo(print())
				.andExpect(status().isNoContent());

		this.mvc.perform(get("/devices/1"))
				.andDo(print())
				.andExpect(status().is(204));
	}
}