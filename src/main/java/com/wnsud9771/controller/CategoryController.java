package com.wnsud9771.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wnsud9771.dto.MainCategoryList;
import com.wnsud9771.dto.ProductDTO;
import com.wnsud9771.service.product.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorys")
@Slf4j
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {
	private final CategoryService categoryService;
	
	@Operation(summary = "메인 카테고리 이름들 조회", description = "")
	@GetMapping("/allmaincategory")
	public List<MainCategoryList> getallMainCategorys() {
		return categoryService.searchMainCategoryList();
	}
	
	
}
