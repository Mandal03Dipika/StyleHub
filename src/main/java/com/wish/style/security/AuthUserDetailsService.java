package com.wish.style.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.wish.style.entities.User;
import com.wish.style.repository.UserRepository;

@Service
public class AuthUserDetailsService implements UserDetailsService{

	@Autowired
	UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userRepository.findByUsername(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("User Not Found");
		}
		return new UserSingleton(user);
	}
}
