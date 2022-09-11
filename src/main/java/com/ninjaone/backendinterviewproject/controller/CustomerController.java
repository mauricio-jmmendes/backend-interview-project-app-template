package com.ninjaone.backendinterviewproject.controller;

import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import com.ninjaone.backendinterviewproject.service.CustomerService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@GetMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
	public ResponseEntity<CustomerDTO> findById(@PathVariable("id") Long id) {
		CustomerDTO customerDTO;
		try {
			customerDTO = customerService.findById(id);
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(customerDTO);
	}

	@GetMapping()
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<CustomerDTO>> findAll() {
		return ResponseEntity.ok(customerService.findAll());
	}

	@PutMapping(path = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN') or #id == authentication.principal.id")
	public ResponseEntity<String> updateCustomer(@PathVariable("id") Long id,
																							 @RequestBody CustomerDTO customerDTO) {
		try {
			customerService.mergeEntity(id, customerDTO);
			return ResponseEntity.ok("");
		} catch (ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@DeleteMapping(value = "/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		customerService.deleteCustomer(id);
		return ResponseEntity.noContent().build();
	}
}
