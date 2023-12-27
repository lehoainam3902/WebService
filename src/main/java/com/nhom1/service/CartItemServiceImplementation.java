 package com.nhom1.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nhom1.exception.CartItemException;
import com.nhom1.exception.UserExCeption;
import com.nhom1.model.Cart;
import com.nhom1.model.CartItem;
import com.nhom1.model.Product;
import com.nhom1.model.User;
import com.nhom1.repository.CartItemRepository;
import com.nhom1.repository.CartRepository;
@Service
public class CartItemServiceImplementation implements CartItemService{

	private CartItemRepository cartItemRepository;
	private UserService userService;
	private CartRepository cartRepository;
	
	public CartItemServiceImplementation(CartItemRepository cartItemRepository,
			UserService userService,
			CartRepository cartRepository) {
		this.cartItemRepository=cartItemRepository;
		this.userService=userService;
		this.cartRepository=cartRepository;
	}
	
	@Override
	public CartItem createCartItem(CartItem cartitem) {
		cartitem.setQuantity(1);
		cartitem.setPrice(cartitem.getProduct().getPrice()*cartitem.getQuantity());
		cartitem.setDiscountedPrice(cartitem.getProduct().getDiscountedPrice()*cartitem.getQuantity());
		
		CartItem createCartItem = cartItemRepository.save(cartitem);
		
		return createCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartitem) throws CartItemException, UserExCeption {
		
		CartItem item = findCartItemById(id);
		User user = userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId)) {
			item.setQuantity(cartitem.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
		}
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserExCeption {
		CartItem cartItem = findCartItemById(cartItemId);
		
		User user = userService.findUserById(cartItem.getUserId());
		
		User reqUser = userService.findUserById(userId);
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		}
		else {
			throw new UserExCeption("Không thể xóa của người dùng khác");
		}
	}

	@Override
	public CartItem findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItem> opt = cartItemRepository.findById(cartItemId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("Không tìm thấy mặt hàng với mã: " +cartItemId);
	}

}
