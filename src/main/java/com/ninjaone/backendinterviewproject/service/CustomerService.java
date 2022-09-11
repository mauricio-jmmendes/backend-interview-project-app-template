package com.ninjaone.backendinterviewproject.service;

import com.ninjaone.backendinterviewproject.common.AppConstants;
import com.ninjaone.backendinterviewproject.database.CustomerRepository;
import com.ninjaone.backendinterviewproject.exception.ResourceNotFoundException;
import com.ninjaone.backendinterviewproject.mappings.CustomerMapper;
import com.ninjaone.backendinterviewproject.model.Customer;
import com.ninjaone.backendinterviewproject.model.dto.CustomerDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	private final CustomerMapper customerMapper;

	public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
		this.customerRepository = customerRepository;
		this.customerMapper = customerMapper;
	}

	public CustomerDTO saveEntity(CustomerDTO customerDTO) {
		Customer customer = customerMapper.toEntity(customerDTO);
		customerRepository.save(customer);
		return customerMapper.toDTO(customer);
	}

	public CustomerDTO findById(final Long id) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "id", id));
		return customerMapper.toDTO(customer);
	}

	public CustomerDTO findByEmail(String email) throws ResourceNotFoundException {
		Customer customer = customerRepository.findByEmail(email)
																					.orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "email", email));
		return customerMapper.toDTO(customer);
	}

	public List<CustomerDTO> findAll() {
		return customerRepository.findAll().stream().map(customerMapper::toDTO)
														 .collect(Collectors.toList());
	}

	public void mergeEntity(Long id, CustomerDTO customerDTO) throws ResourceNotFoundException {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CUSTOMER, "id", id));
		customerMapper.update(customerDTO, customer);
		customerRepository.save(customer);
	}

	public void deleteCustomer(Long id) {
		customerRepository.deleteById(id);
	}
}
