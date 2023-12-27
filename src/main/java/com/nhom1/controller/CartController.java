package com.nhom1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.exception.ProductException;
import com.nhom1.exception.UserExCeption;
import com.nhom1.model.Cart;
import com.nhom1.model.User;
import com.nhom1.request.AddItemRequest;
import com.nhom1.response.ApiResponse;
import com.nhom1.service.CartService;
import com.nhom1.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/cart")
@Tag(name = "Cart Management", description = "chọn giỏ hàng khách hàng, thêm sản phẩm vào giỏ hàng")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	@Operation(description = "Tìm giỏ hàng theo mã khách hàng")
	public ResponseEntity<Cart>findUserCart(@RequestHeader("Authorization") String jwt) throws UserExCeption{
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		
		return new ResponseEntity<Cart>(cart, HttpStatus.OK);
		
	}
	
	@PutMapping("/add")
	@Operation(description = "Thêm sản phẩm vào giỏ hàng")
	public ResponseEntity<ApiResponse>addItemToCart(@RequestBody AddItemRequest req,
			@RequestHeader("Authorization") String jwt) throws UserExCeption, ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		
		cartService.addCartItem(user.getId(), req);
		
		ApiResponse res = new ApiResponse();
		res.setMessage ("Sản phẩm đã được thêm vào giỏ hàng");
		res.setStatus(true);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}

