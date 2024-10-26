package com.wish.style.service.implement;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wish.style.dto.DocumentFolderDto;
import com.wish.style.dto.LoginDto;
import com.wish.style.dto.LoginRequestDto;
import com.wish.style.dto.LoginResponseDto;
import com.wish.style.dto.RefreshTokenDto;
import com.wish.style.dto.RegisterDto;
import com.wish.style.dto.UserResponseDto;
import com.wish.style.entities.User;
import com.wish.style.repository.UserRepository;
import com.wish.style.security.JwtService;
import com.wish.style.service.IAuthService;
import com.wish.style.service.IDocumentService;
import com.wish.style.service.IUserService;

@Service
public class AuthService implements IAuthService{
	
	@Autowired
	ModelMapper mapper;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	LoginResponseDto loginResponseDto;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired 
	IUserService userService;
	
	@Autowired 
	LoginRequestDto loginRequestDto;
	
	@Autowired
	IDocumentService docService;
	
	@Override
	public UserResponseDto registerUser(RegisterDto registerDto) {
		User user=mapper.map(registerDto, User.class);
		user.setPassword(bCryptPasswordEncoder.encode(registerDto.getPassword()));
		user.setCreatedAt(new Date(System.currentTimeMillis()));
		userRepository.save(user);
		DocumentFolderDto folder=new DocumentFolderDto();
		folder.setFile_name(user.getUsername());
		folder.setOriginal_name(user.getUsername());
		folder.setUserId(user.getId());
		docService.createFolder(folder);
		return mapper.map(user, UserResponseDto.class);
	}
	
	@Override
	public LoginResponseDto checkLogin(LoginDto loginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginDto.getUsername(), loginDto.getPassword()));
		
		if(authentication.isAuthenticated())
		{
			String token=jwtService.generateToken(loginDto.getUsername());
			String refreshToken=jwtService.generateRefreshToken(loginDto.getUsername());
			loginResponseDto.setRefreshToken(refreshToken);
			loginResponseDto.setToken(token);
			return loginResponseDto;
		}
		throw new UsernameNotFoundException("User Not Found");
	}
	
	@Override
	public LoginResponseDto refreshToken(RefreshTokenDto refreshTokenDto)
	{
		String username=jwtService.extractUsername(refreshTokenDto.getRefreshToken());
		var user=userRepository.findByUsername(username);
		if(jwtService.validateToken2(refreshTokenDto.getRefreshToken(), username))
		{
			String token=jwtService.generateToken(username);
			String refreshToken=jwtService.generateRefreshToken(user.getUsername());
			loginResponseDto.setRefreshToken(refreshToken);
			loginResponseDto.setToken(token);
			return loginResponseDto;
		}
		return null;
	}

	@Override
	public User userFromToken(RefreshTokenDto refreshTokenDto) {
		String username=jwtService.extractUsername(refreshTokenDto.getRefreshToken());
		User user=userRepository.findByUsername(username);
		return user;
	}
	
}
