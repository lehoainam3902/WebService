package com.nhom1.service;

import com.nhom1.exception.CartItemException;
import com.nhom1.exception.UserExCeption;
import com.nhom1.model.Cart;
import com.nhom1.model.CartItem;
import com.nhom1.model.Product;

public interface CartItemService {

	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem	updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserExCeption; 

	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);

	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException,UserExCeption;
	
	public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
