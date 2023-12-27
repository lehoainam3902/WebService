package com.nhom1.service;

import java.util.List;

import com.nhom1.exception.OrderException;
import com.nhom1.model.Address;
import com.nhom1.model.Order;
import com.nhom1.model.User;

public interface OrderService {
	public Order createOrder(User user, Address shippAdress);
	
	public Order findOrderById(Long orderId) throws OrderException;
	
	public List<Order> usersOrderHistory (Long userId);
	
	public Order placedOrder(Long orderId) throws OrderException;
	
	public Order confirmedOrder(Long orderId) throws OrderException;
	
	public Order shippedOrder(Long orderId) throws OrderException;
	
	public Order delivereOrder(Long orderId) throws OrderException;
	
	public Order canceledOrder(Long orderId) throws OrderException;

	public List<Order>getAllOrders();
	
	public void deleteOrder(Long orderId) throws OrderException;

}
