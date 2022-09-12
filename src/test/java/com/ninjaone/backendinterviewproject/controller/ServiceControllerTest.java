package com.ninjaone.backendinterviewproject.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ninjaone.backendinterviewproject.common.AppConstants.DeviceType;
import com.ninjaone.backendinterviewproject.common.AppConstants.ServiceStatus;
import com.ninjaone.backendinterviewproject.common.AppConstants.ServiceType;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.mappings.ServiceMapper;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.ServiceOrderDTO;
import com.ninjaone.backendinterviewproject.util.AbstractRestControllerTest;
import java.math.BigDecimal;
import java.time.LocalDate;
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
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ServiceControllerTest extends AbstractRestControllerTest {

	@Autowired
	private ServiceMapper serviceMapper;

	@Autowired
	private DeviceRepository deviceRepository;

	private ServiceOrder serviceOrder;

	@BeforeAll()
	@Transactional
	public void setUp() throws Exception {
		super.setUp();
		serviceOrder = new ServiceOrder(ServiceType.DEVICE_MAINTENANCE.name(),
																		"Regular monthly maintenance",
																		BigDecimal.valueOf(100.00),
																		ServiceStatus.DONE);

		serviceOrder.setExecutionDate(LocalDate.now().minusDays((int) (Math.random() * 30 + 1)));
		Device device = new Device("Windows Server X", DeviceType.WINDOWS_SERVER.name());
		device.addService(serviceOrder);
		deviceRepository.save(device);

		this.getCustomer().addDevice(device);
		this.getCustomerRepository().save(this.getCustomer());
	}

	@Test
	@Order(1)
	void findById() throws Exception {
		this.getMvc().perform(get("/services/" + serviceOrder.getId()).header("Authorization", this.getToken()))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(2)
	void notFoundById() throws Exception {
		this.getMvc().perform(get("/services/10").header("Authorization", this.getToken()))
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	@Order(3)
	void findAll() throws Exception {
		this.getMvc().perform(get("/services").header("Authorization", this.getToken()))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(4)
	void updateServices() throws Exception {
		//String token = getTokenForLogin("jhon.doe", "123456", getMockMvc());
		ServiceOrderDTO dto = serviceMapper.toDTO(serviceOrder);
		dto.setCost(BigDecimal.valueOf(4));

		this.getMvc().perform(put("/services")
															.header("Authorization", this.getToken())
															.content(getObjectMapper().writeValueAsString(dto))
															.contentType("application/json"))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	@Order(5)
	void deleteServices() throws Exception {
		this.getMvc().perform(delete("/services/" + serviceOrder.getId()).header("Authorization", this.getToken()))
				.andDo(print())
				.andExpect(status().isNoContent());

		this.getMvc().perform(get("/services/" + serviceOrder.getId()))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
}