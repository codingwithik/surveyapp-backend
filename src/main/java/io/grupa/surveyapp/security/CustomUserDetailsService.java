package io.grupa.surveyapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.grupa.surveyapp.entities.User;
import io.grupa.surveyapp.services.UserService;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


		User user = userService.findByPhoneNumberOrEmail(username).orElse(null);
		// update user last login
		if (user != null)
			return new CustomUserDetails(user);

		
		return new CustomUserDetails();
	}

}
