package com.wish.style.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wish.style.dto.UserResponseDto;
import com.wish.style.entities.User;
import com.wish.style.repository.UserRepository;
import com.wish.style.service.IUserService;

@Service
public class UserService implements IUserService{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public List<UserResponseDto> getAllUser() {
		List<User> users=  userRepository.findAll();
        return users.stream().map(user->mapper.map(user,UserResponseDto.class))
                .collect(Collectors.toList());
	}

	@Override
	public UserResponseDto getUserById(Long id) {
		User user=  userRepository.findById(id).orElseThrow();
        return  mapper.map(user,UserResponseDto.class);
	}

}
