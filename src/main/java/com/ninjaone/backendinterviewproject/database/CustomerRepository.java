package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.Customer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByNickname(String nickname);

	Optional<Customer> findByEmail(String email);

	Boolean existsByNickname(String name);

	Boolean existsByEmail(String email);

}
