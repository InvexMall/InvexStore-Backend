package com.wnsud9771.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String imgsrc; // 이미지 소스 경로
	private String productname; // 제품 이름
	private String productprice; //제품 가격
	private Long discountrate; // 할인율
	private Long rating; //평점
	
	
}
