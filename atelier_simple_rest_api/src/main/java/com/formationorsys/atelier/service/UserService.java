package com.formationorsys.atelier.service;

import com.formationorsys.atelier.models.User;

public interface UserService {
	
	// Generate API Key & Secret for a new user
	User generateApiKey(String username);
	
	// Validate API Key & Secret
    public boolean isValidApiKey(String apiKey, String apiSecret);
}
