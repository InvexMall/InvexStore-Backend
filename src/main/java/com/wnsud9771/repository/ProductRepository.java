package com.wnsud9771.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wnsud9771.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>{

}
