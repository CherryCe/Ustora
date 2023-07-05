package com.edu.hutech.major.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.hutech.major.model.Order;
import com.edu.hutech.major.repository.OrderRepository;
import com.edu.hutech.major.service.OrderService;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	public List<Order> getAllOrder() {
		return orderRepository.findAll();
	}

	public void updateOrder(Order order) {
		orderRepository.save(order);
	}

	public void removeOrderById(int id) {
		orderRepository.deleteById(id);
	}

	public Optional<Order> getOrderById(int id) {
		return orderRepository.findById(id);
	}

	public List<Order> getAllOrderByUserId(int id) {
		return orderRepository.findAllByUserId(id);
	}

	public Optional<Order> getOrderBySerialNumber(String serialNumber) {
		return orderRepository.findBySerialNumber(serialNumber);
	}
}
