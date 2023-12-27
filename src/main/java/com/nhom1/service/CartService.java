package com.nhom1.service;

import com.nhom1.exception.ProductException;
import com.nhom1.model.Cart;
import com.nhom1.model.User;
import com.nhom1.request.AddItemRequest;


public interface CartService {
	
	public Cart createCart(User user);
	
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException;
	
	public Cart findUserCart(Long userId);
	
}
