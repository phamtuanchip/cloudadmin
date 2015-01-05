package com.cloud.admin.dao;

import java.util.List;

import com.cloud.admin.model.User;

public interface UserDAO {
	
	public User findByUsername(String username);
	public int save(User user);
	public int update(User user);
	public void delete(String username);
	public List<User> listAll();	

}
