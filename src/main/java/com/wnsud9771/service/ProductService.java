package com.wnsud9771.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wnsud9771.dto.ProductDTO;
import com.wnsud9771.entity.Product;
import com.wnsud9771.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	
	public List<ProductDTO> getallProducts() {
		return productRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
	}
	
	private ProductDTO convertToDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		
		dto.setId(product.getId());
		dto.setImgsrc(product.getImgsrc());
		dto.setDiscountrate(product.getDiscountrate());
		dto.setProductname(product.getProductname());
		dto.setProductprice(product.getProductprice());
		dto.setRating(product.getRating());
		
		return dto;
	}
}
