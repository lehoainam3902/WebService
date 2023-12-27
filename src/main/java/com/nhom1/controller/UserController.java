package com.nhom1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.exception.UserExCeption;
import com.nhom1.model.User;
import com.nhom1.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<User> getUserProfileHandler(
			@RequestHeader("Authorization")String jwt) throws UserExCeption{
		
			User user = userService.findUserProfileByJwt(jwt);
			
			return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
	}
}
