package com.winterfoodies.ceo.domain.user.repository;

import com.winterfoodies.ceo.entities.Store;
import com.winterfoodies.ceo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
