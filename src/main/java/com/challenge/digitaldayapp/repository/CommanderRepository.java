package com.challenge.digitaldayapp.repository;

import com.challenge.digitaldayapp.domain.Commander;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Commander entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommanderRepository extends JpaRepository<Commander, Long> {}
