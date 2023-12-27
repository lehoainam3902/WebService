package com.nhom1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nhom1.model.Cart;
import com.nhom1.model.CartItem;
import com.nhom1.model.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	
	@Query("SELECT ci From CartItem ci Where ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart, @Param("product")Product product, @Param("size")String size,
			@Param("userId")Long userId);
}
