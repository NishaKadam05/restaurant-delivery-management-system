package com.restaurantdelivery.security;

import java.util.Collections;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.restaurantdelivery.entity.User;
import com.restaurantdelivery.enums.UserStatus;
import com.restaurantdelivery.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user1 = userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

		if (user1.getStatus() != UserStatus.ACTIVE) {
			throw new DisabledException("User account is not active");
		}

		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user1.getRole().name());

		return new org.springframework.security.core.userdetails.User(user1.getEmail(), user1.getPassword(),
				Collections.singletonList(authority));
	}

}
