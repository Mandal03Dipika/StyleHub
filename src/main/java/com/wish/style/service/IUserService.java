package com.wish.style.service;

import java.util.List;
import com.wish.style.dto.UserResponseDto;

public interface IUserService {
	List<UserResponseDto> getAllUser();
	UserResponseDto getUserById(Long id);
}
