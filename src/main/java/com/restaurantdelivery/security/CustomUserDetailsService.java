package com.restaurantdelivery.security;

import java.util.Collections;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new UserNameNotFoundException("User not found with email: " + email));

		if (user.getStatus() != UserStatus.ACTIVE) {
			throw new DisabledException("User account is not active");
		}

		GrantedAuthority authority = SimpleGrantedAuthority("ROLE_" + user.getRole().name());

		return new User(user.getEmail(), user.getPassword(), Collections.singletonList(authority));
	}

}
