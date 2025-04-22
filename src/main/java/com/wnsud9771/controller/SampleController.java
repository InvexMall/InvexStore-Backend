package com.wnsud9771.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wnsud9771.dto.SampleDTO;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/sample")
@RequiredArgsConstructor
@Slf4j
public class SampleController {
	
	@Operation(summary = "테스트 ", description = "샘플 테스트")
	@GetMapping("/ss1")	
	public ResponseEntity<SampleDTO> addPipeline() {
		SampleDTO dto = new SampleDTO();
		return ResponseEntity.ok(dto);

		
	}
}
