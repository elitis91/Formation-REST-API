package com.formationorsys.atelier.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formationorsys.atelier.dao.UserDAO;
import com.formationorsys.atelier.models.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserDAO userDAO;

	@Override
	public User generateApiKey(String username) {
		User _user = new User(username);
		return userDAO.save(_user);
	}

	@Override
	public boolean isValidApiKey(String apiKey, String apiSecret) {
		Optional<User> key = userDAO.findByApiKeyAndApiSecret(apiKey, apiSecret);
        return key.isPresent();
	}

}
