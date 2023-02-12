package com.winterfoodies.ceo.domain.product.repository;

import com.winterfoodies.ceo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}