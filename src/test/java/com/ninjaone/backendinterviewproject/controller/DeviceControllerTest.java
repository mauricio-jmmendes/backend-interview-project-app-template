package com.ninjaone.backendinterviewproject.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ninjaone.backendinterviewproject.common.AppConstants.DeviceType;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.util.AbstractRestControllerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestMethodOrder(OrderAnnotation.class)
class DeviceControllerTest extends AbstractRestControllerTest {

	@Autowired
	private DeviceRepository deviceRepository;
	private Device device;

	@BeforeAll
	public void setUp() throws Exception {
		super.setUp();

		device = new Device("Windows Server X", DeviceType.WINDOWS_SERVER.name());
		deviceRepository.save(device);

		this.getCustomer().addDevice(device);
		this.getCustomerRepository().save(this.getCustomer());
	}

	@Test
	@Order(1)
	void findById() throws Exception {
		this.getMvc().perform(get("/devices/" + device.getId()))
				.andDo(print())
				.andExpect(status().is(200));
	}

	@Test
	@Order(2)
	void notFoundById() throws Exception {
		this.getMvc().perform(get("/devices/10"))
				.andDo(print())
				.andExpect(status().is(404));
	}


	@Test
	@Order(3)
	void findAll() throws Exception {
		this.getMvc().perform(get("/devices"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(4)
	void updateDevices() throws Exception {
		this.getMvc().perform(put("/devices")
															.content(String.format("{\"id\": %d,\"type\":\"WINDOWS_SERVER\",\"systemName\":\"Windows Server OS\"}", device.getId()))
															.contentType("application/json"))
				.andDo(print())
				.andExpect(status().is(200));
	}

	@Test
	@Order(5)
	void deleteDevices() throws Exception {

		this.getMvc().perform(delete("/devices/" + device.getId()).contentType("application/json"))
				.andDo(print())
				.andExpect(status().isNoContent());

		this.getMvc().perform(get("/devices/" + device.getId()))
				.andDo(print())
				.andExpect(status().is(404));
	}
}