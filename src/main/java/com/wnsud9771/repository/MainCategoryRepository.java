package com.wnsud9771.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wnsud9771.entity.product.MainCategory;

@Repository
public interface MainCategoryRepository extends JpaRepository <MainCategory, Long>{

}
