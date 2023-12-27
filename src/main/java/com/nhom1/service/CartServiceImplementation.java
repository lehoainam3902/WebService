package com.nhom1.service;

import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.nhom1.exception.ProductException;
import com.nhom1.model.Cart;
import com.nhom1.model.CartItem;
import com.nhom1.model.Product;
import com.nhom1.model.User;
import com.nhom1.repository.CartRepository;
import com.nhom1.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService{

	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	public CartServiceImplementation(CartRepository cartRepository,
		   CartItemService cartItemService,
		   ProductService productService) {
		this.cartRepository=cartRepository;
		this.cartItemService=cartItemService;
		this.productService=productService;
	}
	@Override
	public Cart createCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
				return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		
		CartItem isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if(isPresent == null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price = req.getQuantity()*product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem = cartItemService.createCartItem(cartItem);
			cart.getCartItems().add(createdCartItem);
			
		}
		return "Thêm mặt hàng vào giỏ hàng";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		
		for (CartItem cartItem :cart.getCartItems()) {
			totalPrice = totalPrice + cartItem.getPrice();
			totalDiscountedPrice =totalDiscountedPrice + cartItem.getDiscountedPrice();
			totalItem = totalItem +cartItem.getQuantity();
		}
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscounted(totalDiscountedPrice);
		
		
		return cartRepository.save(cart);
	}

}
