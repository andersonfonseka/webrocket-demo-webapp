package com.andersonfonseka.wr.demo.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserRepository {
	
	private static UserRepository INSTANCE;
	
	private Map<String, User> users = new HashMap<String, User>();
	
	private UserRepository() {
		
		User user = new User();
		user.setFirstName("Admin");
		user.setLastName("Admin");
		user.setUserName("admin");
		user.setPassword("123456");
		user.setRole("1");
		
		users.put("1", user);
	}
	
	public static UserRepository getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new UserRepository();
		}

		return INSTANCE;
	}
	
	public void saveOrUpdate(User user) {
		this.users.put(user.getId(), user);
	}
	

	public User getUserById(String id) {
		return this.users.get(id);
	}
	
	public User getUser(String userName) {
		
		Optional<User> user = this.users.values().stream().filter(x -> x.getUserName().equals(userName)).findFirst();
		
		if (user.isPresent()) {
			return user.get();
		}
		
		return null;
	}
	
	public Collection<User> getUsers(){
		return this.users.values();
	}

}
