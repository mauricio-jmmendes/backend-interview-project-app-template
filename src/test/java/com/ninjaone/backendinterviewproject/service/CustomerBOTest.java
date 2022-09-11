package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.common.AppConstants.DeviceType;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.mappings.CustomerMapper;
import com.ninjaone.backendinterviewproject.mappings.DeviceMapper;
import com.ninjaone.backendinterviewproject.mappings.ServiceMapper;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.model.dto.ServiceDTO;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class CustomerBOTest {

	public static final Long ID = 123L;

	@Mock
	private CustomerRepository customerRepository;

	@Mock
	private DeviceRepository deviceRepository;

	@Mock
	private ServiceOrderBO serviceOrderBO;

	@Mock
	private CustomerMapper customerMapper;

	@Mock
	private DeviceMapper deviceMapper;

	@Mock
	private ServiceMapper serviceMapper;

	@InjectMocks
	private CustomerBO customerBO;

	private Customer customer;

	private CustomerDTO customerDTO;

	private Device device;

	private DeviceDTO deviceDTO;

	private ServiceOrder service;

	private ServiceDTO serviceDTO;

	@BeforeEach
	void setup() {
		device = new Device();
		device.setId(ID);
		device.setSystemName("Windows Server X");
		device.setDeviceType(DeviceType.WINDOWS_SERVER.name());

		deviceDTO = new DeviceDTO(ID, "Windows Server X", DeviceType.WINDOWS_SERVER.name(), List.of());
	}

	@Test
	void saveEntity() {
	}

	@Test
	void findById() {
	}

	@Test
	void findByEmail() {
	}

	@Test
	void findAll() {
	}

	@Test
	void mergeEntity() {
	}

	@Test
	void deleteCustomer() {
	}

	@Test
	void addDevice() {
	}

	@Test
	void orderService() {
	}

	@Test
	void getMontlyStatement() {
	}
}