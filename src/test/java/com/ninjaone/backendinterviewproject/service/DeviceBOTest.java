package com.ninjaone.backendinterviewproject.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.ninjaone.backendinterviewproject.common.AppConstants.DeviceType;
import com.ninjaone.backendinterviewproject.database.DeviceRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.DeviceMapper;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class DeviceBOTest {

	public static final Long ID = 123L;

	@Mock
	private DeviceRepository deviceRepository;

	@Mock
	private DeviceMapper deviceMapper;

	@InjectMocks
	private DeviceBO deviceBO;

	private Device device;

	private DeviceDTO deviceDTO;

	@BeforeEach
	void setup() {
		device = new Device();
		device.setId(ID);
		device.setSystemName("Windows Server X");
		device.setDeviceType(DeviceType.WINDOWS_SERVER.name());

		deviceDTO = new DeviceDTO(ID, "Windows Server X", DeviceType.WINDOWS_SERVER.name(), List.of());

	}

	@Test
	void getDevice() throws ResourceNotFoundException {
		when(deviceRepository.findById(ID)).thenReturn(Optional.of(device));
		when(deviceMapper.toDTO(any(Device.class))).thenReturn(deviceDTO);
		Device storedDevice = deviceBO.getById(ID);
		assertEquals(device.getSystemName(), storedDevice.getSystemName());
	}

	@Test
	void saveDevice() {
		when(deviceRepository.save(any(Device.class))).thenReturn(device);
		when(deviceMapper.toEntity(any(DeviceDTO.class))).thenReturn(device);
		when(deviceMapper.toDTO(any(Device.class))).thenReturn(deviceDTO);
		Device savedDevice = deviceBO.save(deviceDTO);
		assertEquals(device.getDeviceType(), savedDevice.getDeviceType());
	}

	@Test
	void deleteDevice() {
		doNothing().when(deviceRepository).deleteById(ID);
		deviceBO.deleteById(ID);
		Mockito.verify(deviceRepository, times(1)).deleteById(ID);
	}
}
