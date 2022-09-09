package com.ninjaone.backendinterviewproject.database;

import com.ninjaone.backendinterviewproject.model.AuthType;
import com.ninjaone.backendinterviewproject.model.Authority;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Optional<Authority> findByType(AuthType type);
}
