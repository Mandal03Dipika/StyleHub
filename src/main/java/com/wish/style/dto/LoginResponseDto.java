package com.wish.style.dto;

import org.springframework.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LoginResponseDto {
	private String token;
	private String refreshToken;
//	private UserResponseDto data;
}
