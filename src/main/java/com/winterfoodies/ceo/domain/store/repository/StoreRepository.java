package com.winterfoodies.ceo.domain.store.repository;

import com.winterfoodies.ceo.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
