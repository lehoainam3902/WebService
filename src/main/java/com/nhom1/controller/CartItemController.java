package com.nhom1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.exception.CartItemException;
import com.nhom1.exception.UserExCeption;
import com.nhom1.model.CartItem;
import com.nhom1.model.User;
import com.nhom1.response.ApiResponse;
import com.nhom1.service.CartItemService;
import com.nhom1.service.UserService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {
	
	@Autowired
	private CartItemService cartItemService;
	
	@Autowired
	private UserService userService;
	
	@DeleteMapping("/{cartItemId}")
	@Operation(description = "Xóa sản phẩm trong giỏ hàng")
	@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Xóa sản phẩm")
	public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws UserExCeption, CartItemException{
		User user = userService.findUserProfileByJwt(jwt);
		cartItemService.removeCartItem(user.getId(), cartItemId);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Xóa sản phẩm khỏi giỏ hàng");
		res.setStatus(true);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("/{cartItemId}")
	@Operation(description = "Cập nhật sản phẩm vào giỏ hàng")
	public ResponseEntity<CartItem> updateCartItem(
			@RequestBody CartItem cartItem,
			@PathVariable Long cartItemId,
			@RequestHeader("Authorization") String jwt) throws UserExCeption, CartItemException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		CartItem updateCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
		
		return new ResponseEntity<>(updateCartItem, HttpStatus.OK);
	}
}
