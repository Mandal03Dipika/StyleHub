package com.wish.style.service;

import com.wish.style.dto.LoginDto;
import com.wish.style.dto.LoginResponseDto;
import com.wish.style.dto.RefreshTokenDto;
import com.wish.style.dto.RegisterDto;
import com.wish.style.dto.UserResponseDto;
import com.wish.style.entities.User;

public interface IAuthService {
	UserResponseDto registerUser(RegisterDto registerDto);
	LoginResponseDto checkLogin(LoginDto loginDto);
	LoginResponseDto refreshToken(RefreshTokenDto refreshTokenDto);
	User userFromToken(RefreshTokenDto refreshTokenDto);
}
