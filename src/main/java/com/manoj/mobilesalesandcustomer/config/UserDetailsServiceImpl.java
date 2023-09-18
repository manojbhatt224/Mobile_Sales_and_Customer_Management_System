package com.manoj.mobilesalesandcustomer.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.manoj.mobilesalesandcustomer.model.User;
import com.manoj.mobilesalesandcustomer.repository.UserRepository;





@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = repository.findByEmail(username);
		return user.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

	}
}
