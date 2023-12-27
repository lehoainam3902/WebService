package com.nhom1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom1.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
