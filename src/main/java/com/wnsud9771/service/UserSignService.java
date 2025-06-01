package com.wnsud9771.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wnsud9771.dto.SignUpResponseDTO;
import com.wnsud9771.dto.SignUpUserDTO;
import com.wnsud9771.entity.UserEntity;
import com.wnsud9771.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSignService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화용

    // 회원가입 로직
    public SignUpResponseDTO userSignUp(SignUpUserDTO userDTO) {
        try {
            // 1. 입력값 검증
            if (!isValidInput(userDTO)) {
                return new SignUpResponseDTO(false, "입력값이 올바르지 않습니다.");
            }
            
            // 2. 이메일 중복 체크
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                return new SignUpResponseDTO(false, "이미 존재하는 이메일입니다.");
            }
            
            // 3. 전화번호 중복 체크 (필요시)
            if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
                return new SignUpResponseDTO(false, "이미 존재하는 전화번호입니다.");
            }
            
            // 4. 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            
            // 5. DTO -> Entity 변환 및 저장
            UserEntity userEntity = UserEntity.builder()
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .password(encodedPassword) // 암호화된 비밀번호 저장
                .phoneNumber(userDTO.getPhoneNumber())
                .build();
            
            userRepository.save(userEntity);
            
            return new SignUpResponseDTO(true, "회원가입이 완료되었습니다.");
            
        } catch (Exception e) {
            return new SignUpResponseDTO(false, "회원가입 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 로그인 로직 (비밀번호 검증 포함)
    public SignUpResponseDTO userSignIn(String email, String password) {
        try {
            // 1. 이메일로 사용자 찾기
            Optional<UserEntity> userOptional = userRepository.findByEmail(email);
            
            if (userOptional.isEmpty()) {
                return new SignUpResponseDTO(false, "회원가입 되지 않은 이메일입니다.");
            }
            
            UserEntity user = userOptional.get();
            
            // 2. 비밀번호 검증
            if (!passwordEncoder.matches(password, user.getPassword())) {
                return new SignUpResponseDTO(false, "비밀번호가 일치하지 않습니다.");
            }
            
            // 3. 로그인 성공
            return new SignUpResponseDTO(true, "로그인에 성공했습니다.");
            
        } catch (Exception e) {
            return new SignUpResponseDTO(false, "로그인 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    // 사용자 정보 조회 (로그인 성공 후 사용자 정보가 필요할 때)
    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 입력값 검증 메서드
    private boolean isValidInput(SignUpUserDTO userDTO) {
        // 1. null 체크
        if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty() ||
            userDTO.getName() == null || userDTO.getName().trim().isEmpty() ||
            userDTO.getPassword() == null || userDTO.getPassword().trim().isEmpty() ||
            userDTO.getPhoneNumber() == null || userDTO.getPhoneNumber().trim().isEmpty()) {
            return false;
        }
        
        // 2. 이메일 형식 검증
        if (!isValidEmail(userDTO.getEmail())) {
            return false;
        }
        
        // 3. 비밀번호 강도 검증
        if (!isValidPassword(userDTO.getPassword())) {
            return false;
        }
        
       
        
        return true;
    }
    //이메일 검증
    private boolean isValidEmail(String email) {
        return email.contains("@") && email.contains(".") && email.length() > 5;
    }
    
    // 비밀번호 강도 검증
    private boolean isValidPassword(String password) {
        // 최소 8자, 대/소문자, 숫자, 특수문자 포함
        if (password.length() < 8) {
            return false;
        }
        
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
        
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
    
    
}