package com.wnsud9771.entity.product;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SubCategory { // 소분류 카테고리
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String categoryName; //카테고리명
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainCategory_id")
    private MainCategory mainCategory;
}
