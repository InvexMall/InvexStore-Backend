package com.wnsud9771.dto;

import lombok.Data;

@Data
public class ProductDTO {
	
	private Long id; //기본 id
	private String imgsrc; // 이미지 소스 경로
	private String productname; // 제품 이름
	private String productprice; //제품 가격
	private Long discountrate; // 할인율
	private Long rating; //평점
}
