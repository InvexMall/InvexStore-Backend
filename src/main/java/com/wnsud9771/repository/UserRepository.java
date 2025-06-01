package com.wnsud9771.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wnsud9771.entity.UserEntity;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // 이메일로 유저 찾기 메서드
    Optional<UserEntity> findByEmail(String email);
    
    // 회원가입시 중복 체크용 메서드들
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    
    
}