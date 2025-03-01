package com.NBE_4_5_2.Team5.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.NBE_4_5_2.Team5.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
}
