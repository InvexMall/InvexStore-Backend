package com.wnsud9771.entity.product;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class MainCategory { // 대분류 카테고리
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String categoryName; //카테고리명
	
	@OneToMany(mappedBy = "mainCategory", cascade = CascadeType.ALL)
    private List<SubCategory> subCategorylist;
}
