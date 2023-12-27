package com.nhom1.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.exception.OrderException;
import com.nhom1.exception.UserExCeption;
import com.nhom1.model.Address;
import com.nhom1.model.Order;
import com.nhom1.model.User;
import com.nhom1.service.OrderService;
import com.nhom1.service.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<Order>createOrder(@RequestBody Address shippingAddress,
			@RequestHeader("Authorization") String jwt) throws UserExCeption{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Order order = orderService.createOrder(user, shippingAddress);
		
		return new ResponseEntity<Order>(order, HttpStatus.CREATED);
	}

	@GetMapping("/user")
	public ResponseEntity<List<Order>>findOrderById(
			@RequestHeader("Authorization") String jwt) throws UserExCeption{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Order> orders = orderService.usersOrderHistory(user.getId());
		
		return new ResponseEntity<> (orders, HttpStatus.CREATED);
	}
	
	@GetMapping("/{Id}")
	public ResponseEntity<Order> findOrderById(
			@PathVariable("Id") Long orderId,
			@RequestHeader("Authorization") String jwt) throws UserExCeption, OrderException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Order order = orderService.findOrderById(orderId);
		
		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}
}
