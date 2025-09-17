package com.MicroUserService.Service;

import java.util.List;

import com.MicroUserService.Entites.User;

public interface UserService {
	
	//user operations
	
	//create User
	User saveUser(User user);
	 
	//get all user
	List<User> getAllUser();
	
	//get single user of given userid
	User getUser(String userId);
	
	//TODO : Delete and Update

}
