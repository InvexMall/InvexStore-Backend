package com.wnsud9771.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignUpUserDTO {
	@NotBlank(message = "이메일은 필수입니다")
	@Email(message = "이메일 형식이 올바르지 않습니다")
	private String email;
    private String name;        // userName → name
    private String password;
    private String phoneNumber;
}
