package com.wnsud9771.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wnsud9771.dto.ProductDTO;
import com.wnsud9771.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProductController {
	
	private final ProductService productService;
	
	@Operation(summary = "캠페인 관리화면 전체 조회", description = "전체 아이템 목록을 조회합니다.")
	@GetMapping("/all")
	public List<ProductDTO> getallProducts() {
		return productService.getallProducts();
	}
	
}
