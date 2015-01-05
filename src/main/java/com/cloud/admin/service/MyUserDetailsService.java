package com.cloud.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloud.admin.dao.UserDAO;
import com.cloud.admin.model.User;

public class MyUserDetailsService implements UserDetailsService{
	
	//get user from DB
	@Autowired
	public UserDAO userDAO;

	
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		
		User user = userDAO.findByUsername(username);
		
		if(user==null){
			throw new UsernameNotFoundException("The user with name:" + username + "is not found!");
		}
			
		return (UserDetails) user;
	}


}
