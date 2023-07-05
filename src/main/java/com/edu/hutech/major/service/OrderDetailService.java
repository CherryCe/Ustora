package com.edu.hutech.major.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.edu.hutech.major.model.OrderDetail;

@Service
public interface OrderDetailService {

	void updateOrderDetail(OrderDetail orderDetail);

	void removeOrderDetailById(int id);

	List<OrderDetail> getAllOrderByOrderId(int id);

	List<OrderDetail> getAllProductMostOrderDay();

	List<Integer> getQtyDay();

	List<OrderDetail> getAllProductMostOrderWeek();

	List<Integer> getQtyWeek();
}
