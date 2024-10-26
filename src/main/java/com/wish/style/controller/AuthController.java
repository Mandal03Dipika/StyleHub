package com.wish.style.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wish.style.dto.LoginDto;
import com.wish.style.dto.LoginResponseDto;
import com.wish.style.dto.RefreshTokenDto;
import com.wish.style.dto.RegisterDto;
import com.wish.style.dto.UserResponseDto;
import com.wish.style.entities.User;
import com.wish.style.service.IAuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private IAuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register(@RequestBody RegisterDto registerDto)
	{
		UserResponseDto registeredUser=authService.registerUser(registerDto);
		return new ResponseEntity<>(registeredUser,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto)
	{
		LoginResponseDto loginResponseDto=authService.checkLogin(loginDto);
		return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<LoginResponseDto> refresh(@RequestBody RefreshTokenDto refreshTokenDto)
	{
		LoginResponseDto loginResponseDto=authService.refreshToken(refreshTokenDto);
		return new ResponseEntity<>(loginResponseDto,HttpStatus.OK);
	}
	@PostMapping("/profile")
	public User profile(@RequestBody RefreshTokenDto refreshTokenDto)
	{
		User user=authService.userFromToken(refreshTokenDto);
		return user;
	}
}
