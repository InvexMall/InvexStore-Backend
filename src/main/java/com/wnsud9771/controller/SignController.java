package com.wnsud9771.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;

import com.wnsud9771.dto.SignInDTO;
import com.wnsud9771.dto.SignUpResponseDTO;
import com.wnsud9771.dto.SignUpUserDTO;
import com.wnsud9771.entity.UserEntity;
import com.wnsud9771.service.UserSignService;
import com.wnsud9771.component.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class SignController {
	private final UserSignService userSignService;
	private final JwtUtil jwtUtil; // JWT 유틸리티 주입

	/**
	 * 회원가입 API POST /api/auth/signup
	 */
	@PostMapping("/signup")
	public ResponseEntity<SignUpResponseDTO> signUp(@Valid @RequestBody SignUpUserDTO signUpUserDTO,
			BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			String errorMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
			SignUpResponseDTO errorResponse = new SignUpResponseDTO(false, errorMessage);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		try {
			SignUpResponseDTO response = userSignService.userSignUp(signUpUserDTO);

			if (response.isSuccess()) {
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

		} catch (Exception e) {
			SignUpResponseDTO errorResponse = new SignUpResponseDTO(false, "서버 내부 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	/**
	 * 로그인 API POST /api/auth/signin (JWT 적용)
	 */
	@PostMapping("/signin")
	public ResponseEntity<Map<String, Object>> signIn(@RequestBody SignInDTO signInDTO) {
		try {
			SignUpResponseDTO response = userSignService.userSignIn(signInDTO.getEmail(), signInDTO.getPassword());

			Map<String, Object> result = new HashMap<>();

			if (response.isSuccess()) {
				// 사용자 정보 조회 (실제 DB에서)
				Optional<UserEntity> userOpt = userSignService.findUserByEmail(signInDTO.getEmail());

				if (userOpt.isPresent()) {
					UserEntity user = userOpt.get();

					// JWT 토큰 생성
					String jwtToken = jwtUtil.generateToken(user.getEmail(),
							user.getName() != null ? user.getName() : user.getEmail().split("@")[0], (long) user.getId() 																	// 형변환
					);

					// 사용자 정보 생성 부분도 수정
					Map<String, Object> userInfo = new HashMap<>();
					userInfo.put("id", (long) user.getId()); 
					userInfo.put("email", user.getEmail());
					userInfo.put("name", user.getName() != null ? user.getName() : user.getEmail().split("@")[0]);

					result.put("success", true);
					result.put("message", response.getMessage());
					result.put("token", jwtToken);
					result.put("user", userInfo);

					System.out.println("JWT 토큰 생성 완료: " + jwtToken);

					return ResponseEntity.ok(result);
				} else {
					result.put("success", false);
					result.put("message", "사용자 정보를 찾을 수 없습니다.");
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
				}
			} else {
				result.put("success", false);
				result.put("message", response.getMessage());
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
			}

		} catch (Exception e) {
			System.err.println("로그인 에러: " + e.getMessage());
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("success", false);
			errorResponse.put("message", "서버 내부 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	/**
	 * 토큰 검증 API GET /api/auth/verify
	 */
	@GetMapping("/verify")
	public ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String authHeader) {
		Map<String, Object> result = new HashMap<>();

		try {
			if (authHeader == null || !authHeader.startsWith("Bearer ")) {
				result.put("valid", false);
				result.put("message", "토큰이 없습니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
			}

			String token = authHeader.substring(7); // "Bearer " 제거

			if (jwtUtil.validateToken(token)) {
				String email = jwtUtil.getEmailFromToken(token);
				String name = jwtUtil.getNameFromToken(token);
				Long userId = jwtUtil.getUserIdFromToken(token);

				Map<String, Object> userInfo = new HashMap<>();
				userInfo.put("id", userId);
				userInfo.put("email", email);
				userInfo.put("name", name);

				result.put("valid", true);
				result.put("user", userInfo);
				return ResponseEntity.ok(result);
			} else {
				result.put("valid", false);
				result.put("message", "유효하지 않은 토큰입니다.");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
			}

		} catch (Exception e) {
			result.put("valid", false);
			result.put("message", "토큰 검증 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	/**
	 * 이메일 중복 체크 API
	 */
	@GetMapping("/check-email")
	public ResponseEntity<Map<String, Object>> checkEmailDuplicate(@RequestParam String email) {
		try {
			boolean isDuplicate = userSignService.findUserByEmail(email).isPresent();

			Map<String, Object> response = new HashMap<>();
			response.put("available", !isDuplicate);
			response.put("message", isDuplicate ? "이미 사용중인 이메일입니다." : "사용 가능한 이메일입니다.");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("available", false);
			errorResponse.put("message", "이메일 확인 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	/**
	 * 헬스체크 API
	 */
	@GetMapping("/health")
	public ResponseEntity<String> healthCheck() {
		return ResponseEntity.ok("Auth Service with JWT is running!");
	}
}