package com.nhom1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nhom1.exception.OrderException;
import com.nhom1.model.Order;
import com.nhom1.response.ApiResponse;
import com.nhom1.service.OrderService;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/")
	public ResponseEntity<List<Order>>getAllOrersHandler(){
		List<Order> orders = orderService.getAllOrders();
		return new ResponseEntity<List<Order>>(orders, HttpStatus.ACCEPTED);
	}
	
	@PutMapping("/{orderId}/confirmed")
	public ResponseEntity<Order> ConfirmedOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt) throws OrderException{
		
		Order order = orderService.confirmedOrder(orderId);
		
		return new ResponseEntity<>(order,HttpStatus.OK);
	}
	
	@PutMapping("/{orderId}/ship")
	public ResponseEntity<Order> ShippedHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt)throws OrderException{
		
		Order order = orderService.shippedOrder(orderId);
		
		return new ResponseEntity<>(order,HttpStatus.OK);
		
	}
	
	@PutMapping("/{orderId}/deliver")
	public ResponseEntity<Order> DeliverOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		
		Order order = orderService.delivereOrder(orderId);
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	
	}
	
	@PutMapping("/{orderId}/cancel")
	public ResponseEntity<Order> CancelOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization")String jwt) throws OrderException{
		
		Order order = orderService.canceledOrder(orderId);
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
	
	@DeleteMapping("/{orderId}/delete")
	public ResponseEntity<ApiResponse> DeleteOrderHandler(@PathVariable Long orderId,
			@RequestHeader("Authorization") String jwt) throws OrderException{
		
		orderService.deleteOrder(orderId);
		
		ApiResponse res = new ApiResponse();
		res.setMessage("Đã xóa đơn đặt hàng!!");
		res.setStatus(true);
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
