package com.gilson.helpdesk.api.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gilson.helpdesk.api.entity.User;
import com.gilson.helpdesk.api.security.jwt.JwtUserFactory;
import com.gilson.helpdesk.api.service.UserService;

//Manipulador da interface UserDetail
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = this.userService.findByEmail(email);
		if(user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'", email));
		}else {
			return JwtUserFactory.create(user);
		}
	}

}
