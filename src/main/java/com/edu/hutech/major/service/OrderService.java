package com.edu.hutech.major.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edu.hutech.major.model.Order;

@Service
public interface OrderService {
	List<Order> getAllOrder();

	void updateOrder(Order order);

	void removeOrderById(int id);

	Optional<Order> getOrderById(int id);

	List<Order> getAllOrderByUserId(int id);

	Optional<Order> getOrderBySerialNumber(String serialNumber);
}
