package com.wish.style.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
	 String generateToken(UserDetails userDetails) ;
	 String generateRefreshToken(String username);
	 String extractUsername(String token);
     boolean isTokenValid(String token, UserDetails userDetails);
}
