package com.formationorsys.atelier.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.formationorsys.atelier.models.User;

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
	Optional<User> findByApiKeyAndApiSecret(String apiKey,String apiSecret);
}
