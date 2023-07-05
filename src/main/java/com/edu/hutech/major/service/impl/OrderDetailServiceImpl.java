package com.edu.hutech.major.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.edu.hutech.major.model.OrderDetail;
import com.edu.hutech.major.repository.OrderDetailRepository;
import com.edu.hutech.major.service.OrderDetailService;

@Component
public class OrderDetailServiceImpl implements OrderDetailService {

	@Autowired
	OrderDetailRepository orderDetailRepository;

	@Override
	public void updateOrderDetail(OrderDetail orderDetail) {
		orderDetailRepository.save(orderDetail);
	}

	public void removeOrderDetailById(int id) {
		orderDetailRepository.deleteById(id);
	}

	public List<OrderDetail> getAllOrderByOrderId(int id) {
		return orderDetailRepository.findAllByOrderId(id);
	}

	public List<OrderDetail> getAllProductMostOrderDay() {
		return orderDetailRepository.getAllProductMostOrderDay();
	}

	public List<Integer> getQtyDay() {
		return orderDetailRepository.getQtyDay();
	}

	public List<OrderDetail> getAllProductMostOrderWeek() {
		return orderDetailRepository.getAllProductMostOrderWeek();
	}

	public List<Integer> getQtyWeek() {
		return orderDetailRepository.getQtyWeek();
	}
}
