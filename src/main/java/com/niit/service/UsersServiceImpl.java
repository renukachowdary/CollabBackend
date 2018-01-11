package com.niit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.niit.dao.UsersDAO;
import com.niit.model.UsersDetails;

@Service
public class UsersServiceImpl implements UsersService {

	@Autowired
	UsersDAO usersDAO;

	public boolean saveOrUpdate(UsersDetails users) {

		return usersDAO.saveOrUpdate(users);
	}

	public void delete(UsersDetails user) {
		usersDAO.delete(user);
		
	}

	public UsersDetails getUser(String username) {
		return usersDAO.getUserByUsername(username);
	}

	public UsersDetails viewUser(int userid) {
		
		return usersDAO.viewUser(userid);
	}

	public List<UsersDetails> UserList() {
	
		return usersDAO.UserList();
	}

	public UsersDetails login(String username,String password) {
		
		return usersDAO.login(username,password);
	}

	public boolean isUsernameValid(String username) {
	
		return usersDAO.isUsernameValid(username);
	}

	public boolean isEmailValid(String email) {
	
		return usersDAO.isEmailValid(email);
	}

	public UsersDetails updateUser(UsersDetails users) {

		return usersDAO.updateUser(users);
	}

	
	
}
