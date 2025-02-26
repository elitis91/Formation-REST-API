package com.formationorsys.atelier.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.formationorsys.atelier.models.User;
import com.formationorsys.atelier.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(path = "/generate", produces = "application/json")
	public ResponseEntity<User> generateApiKey(@RequestParam String username){
		User _user = userService.generateApiKey(username);
		return new ResponseEntity<User>(_user,HttpStatus.OK);
	}
}
