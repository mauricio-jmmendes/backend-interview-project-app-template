package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.controller.domain.CreateServiceOrderRequest;
import com.ninjaone.backendinterviewproject.controller.domain.MontlyStatementResponse;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.model.dto.DeviceDTO;
import com.ninjaone.backendinterviewproject.security.AuthenticatedUser;
import com.ninjaone.backendinterviewproject.security.LoggedUser;
import com.ninjaone.backendinterviewproject.service.CustomerBO;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerBO customerBO;

	public CustomerController(CustomerBO customerBO) {
		this.customerBO = customerBO;
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
	public ResponseEntity<CustomerDTO> findById(@PathVariable("id") Long id) {
		Customer storedCustomer = customerBO.findById(id);
		return ResponseEntity.ok(customerBO.mapper().toDTO(storedCustomer));
	}

	@GetMapping()
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<CustomerDTO>> findAll() {
		return ResponseEntity.ok(customerBO.findAll().stream().map(customerBO.mapper()::toDTO).collect(Collectors.toList()));
	}

	@PutMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
	public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable("id") Long id, @RequestBody CustomerDTO customerDTO) {
		Customer updatedCustomer = customerBO.update(id, customerDTO);
		return ResponseEntity.ok(customerBO.mapper().toDTO(updatedCustomer));
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		customerBO.delete(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping(path = "/devices/")
	public ResponseEntity<CustomerDTO> addDevice(@LoggedUser AuthenticatedUser authUser, @Valid DeviceDTO deviceDTO) {
		CustomerDTO savedCustomer = customerBO.addDevice(authUser, deviceDTO);
		return ResponseEntity.ok(savedCustomer);
	}

	@PostMapping(path = "/services/")
	public ResponseEntity<CustomerDTO> createServiceOrder(@LoggedUser AuthenticatedUser authUser, @Valid CreateServiceOrderRequest request) {
		CustomerDTO savedCustomer = customerBO.createServiceOrder(authUser, request);
		return ResponseEntity.ok(savedCustomer);
	}

	@GetMapping(path = "/services/monthly-statement")
	public ResponseEntity<MontlyStatementResponse> getMontlyStatement(@LoggedUser AuthenticatedUser authUser) {
		MontlyStatementResponse montlyStatementResponse = customerBO.getMontlyStatement(authUser);
		return ResponseEntity.ok(montlyStatementResponse);
	}
}
