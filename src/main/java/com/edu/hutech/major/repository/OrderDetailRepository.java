package com.edu.hutech.major.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.edu.hutech.major.model.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

	List<OrderDetail> findAllByOrderId(int orderId);

	@Query(value = "SELECT *, SUM(quantity) AS qty FROM orders os join order_detail od on os.orders_id = od.order_order_id WHERE CONVERT(os.date_time, DATE) = CURDATE() GROUP BY product_order_id ORDER BY qty DESC", nativeQuery = true)
	List<OrderDetail> getAllProductMostOrderDay();

	@Query(value = "SELECT SUM(quantity) AS qty FROM orders os join order_detail od on os.orders_id = od.order_order_id WHERE CONVERT(os.date_time, DATE) = CURDATE() GROUP BY product_order_id ORDER BY qty DESC", nativeQuery = true)
	List<Integer> getQtyDay();

	@Query(value = "SELECT *, SUM(quantity) AS qty FROM orders os join order_detail od on os.orders_id = od.order_order_id WHERE WEEK(CONVERT(os.date_time, DATE)) = WEEK(CURDATE()) GROUP BY product_order_id ORDER BY qty DESC", nativeQuery = true)
	List<OrderDetail> getAllProductMostOrderWeek();

	@Query(value = "SELECT SUM(quantity) AS qty FROM orders os join order_detail od on os.orders_id = od.order_order_id WHERE WEEK(CONVERT(os.date_time, DATE)) = WEEK(CURDATE()) GROUP BY product_order_id ORDER BY qty DESC", nativeQuery = true)
	List<Integer> getQtyWeek();
}
