package com.ninjaone.backendinterviewproject.service;

import static com.ninjaone.backendinterviewproject.controller.domain.MontlyStatementResponse.Statement;

import com.ninjaone.backendinterviewproject.common.AppConstants;
import com.ninjaone.backendinterviewproject.common.AppConstants.ServiceStatus;
import com.ninjaone.backendinterviewproject.controller.domain.CreateServiceOrderRequest;
import com.ninjaone.backendinterviewproject.controller.domain.MontlyStatementResponse;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.CustomerMapper;
import com.ninjaone.backendinterviewproject.mappings.DeviceMapper;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.Device;
import com.ninjaone.backendinterviewproject.model.ServiceOrder;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.security.AuthenticatedUser;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CustomerBO {

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	private final DeviceMapper deviceMapper;

	public CustomerBO(CustomerRepository customerRepository, CustomerMapper customerMapper, DeviceMapper deviceMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
		this.deviceMapper = deviceMapper;
	}

	private static BigDecimal totalPriceFrom(List<Statement> statements) {
		return statements.stream().map(Statement::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private static Device getDeviceById(Customer customer, Long deviceId) {
		return customer.getDevices().stream().filter(d -> d.getId().equals(deviceId)).findFirst()
									 .orElseThrow(() -> new ResourceNotFoundException(AppConstants.DEVICE, "id", deviceId));
	}

	public Customer save(CustomerDTO customerDTO) {
		Customer customer = customerMapper.toEntity(customerDTO);
		customerRepository.save(customer);
		return customer;
	}

	public Customer findById(final Long id) throws ResourceNotFoundException {
		return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "id", id));
	}

	public Customer findByEmail(String email) throws ResourceNotFoundException {
		return customerRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "email", email));
	}

	public List<Customer> findAll() {
		return customerRepository.findAll();
	}

	public Customer update(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "id", id));
		customerMapper.update(customerDTO, customer);
		return customerRepository.save(customer);
	}

	public void delete(Long id) {
		customerRepository.deleteById(id);
	}

	public CustomerDTO addDevice(AuthenticatedUser currentUser, DeviceDTO deviceDTO) {
		Device device = deviceMapper.toEntity(deviceDTO);
		Customer customer = customerRepository.findById(currentUser.getId())
																					.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "id", currentUser.getId()));
		customer.addDevice(device);
		customerRepository.save(customer);
		return customerMapper.toDTO(customer);
	}

	public CustomerDTO createServiceOrder(AuthenticatedUser currentUser, CreateServiceOrderRequest osr) {
		Customer customer = customerRepository.findById(currentUser.getId())
																					.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "id", currentUser.getId()));
		Device device = getDeviceById(customer, osr.getDeviceId());

		ServiceOrder service = new ServiceOrder(osr.getType(), osr.getDescription(), osr.getCost(), osr.getStatus());
		if (ServiceStatus.DONE.equals(service.getStatus())) {
			int randomNum = new Random().nextInt(30);
			service.setExecutionDate(LocalDate.now().minusDays(randomNum));
		}
		device.addService(service);
		customer.addDevice(device);
		customerRepository.save(customer);
		return customerMapper.toDTO(customer);
	}

	public MontlyStatementResponse getMontlyStatement(AuthenticatedUser currentUser) {
		Customer customer = customerRepository.findById(currentUser.getId())
																					.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "id", currentUser.getId()));
		List<Statement> statements = customer.getDevices().stream().map(toMontlyStatementResponse()).flatMap(Collection::stream).collect(Collectors.toList());
		return MontlyStatementResponse.builder().period(Period.ofDays(30)).statements(statements).totalPrice(totalPriceFrom(statements)).build();
	}

	private Function<Device, List<Statement>> toMontlyStatementResponse() {
		return d -> d.getServices().stream().takeWhile(this::isInLastMonth).map(toStatement(d)).collect(Collectors.toList());
	}

	private Boolean isInLastMonth(ServiceOrder so) {
		return ServiceStatus.DONE.equals(so.getStatus())
					 && so.getExecutionDate().isAfter(LocalDate.now().minusMonths(1L));
	}

	private Function<ServiceOrder, Statement> toStatement(Device d) {
		return s -> Statement.builder().date(s.getExecutionDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
												 .serviceDescription(s.getDescription())
												 .deviceDescription(d.getDeviceType())
												 .price(s.getCost())
												 .build();
	}

	public CustomerMapper mapper() {
		return customerMapper;
	}

	public CustomerRepository repository() {
		return customerRepository;
	}
}
