package com.wnsud9771.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wnsud9771.entity.product.SubCategory;

@Repository
public interface SubCategoryRepsotiroy extends JpaRepository <SubCategory, Long>{

}
