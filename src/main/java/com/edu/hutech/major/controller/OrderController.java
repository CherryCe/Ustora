package com.edu.hutech.major.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.edu.hutech.major.dto.OrderDTO;
import com.edu.hutech.major.dto.UserDTO;
import com.edu.hutech.major.global.GlobalData;
import com.edu.hutech.major.model.Order;
import com.edu.hutech.major.model.OrderDetail;
import com.edu.hutech.major.model.Product;
import com.edu.hutech.major.model.User;
import com.edu.hutech.major.service.OrderDetailService;
import com.edu.hutech.major.service.OrderService;
import com.edu.hutech.major.service.ProductService;
import com.edu.hutech.major.service.UserService;

@Controller
public class OrderController {

	private static final Timestamp timestamp = new Timestamp(System.currentTimeMillis());

	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyMMddHHmm");

	@Autowired
	UserService userService;

	@Autowired
	ProductService productService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderDetailService;

	@GetMapping("/order-placed")
	public String orderPlaced(Model model) {
		UserDTO currentUser = new UserDTO();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUsername = ((UserDetails) principal).getUsername();
		User user = userService.getUserByEmail(currentUsername).get();
		currentUser.setEmail(user.getEmail());
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		model.addAttribute("userDTO", currentUser);
		model.addAttribute("orders", orderService.getAllOrderByUserId(user.getId()));
		return "orderPlaced";
	}

	@GetMapping("/order-detail/{orderId}")
	public String orderDetail(@PathVariable int orderId, Model model) {
		UserDTO currentUser = new UserDTO();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUsername = ((UserDetails) principal).getUsername();
		User user = userService.getUserByEmail(currentUsername).get();
		currentUser.setEmail(user.getEmail());
		currentUser.setFirstName(user.getFirstName());
		currentUser.setLastName(user.getLastName());
		model.addAttribute("userDTO", currentUser);
		model.addAttribute("orders", orderDetailService.getAllOrderByOrderId(orderId));
		return "orderDetail";
	}

	@PostMapping("/checkout/products/add")
	public String postCheckoutAdd(@ModelAttribute("orderDTO") OrderDTO orderDTO) {

		UserDTO currentUser = new UserDTO();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currentUsername = ((UserDetails) principal).getUsername();
		User user = userService.getUserByEmail(currentUsername).get();
		Order order = new Order();
		order.setSerialNumber(dtf.format(LocalDateTime.now()));
		order.setDateTime(timestamp);
		order.setUser(userService.getUserById(user.getId()).get());
		order.setTotal(GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());

		order.setStatus("delivery");
		orderService.updateOrder(order);

		for (int i = 0; i < GlobalData.cart.size(); i++) {

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setOrder(orderService.getOrderBySerialNumber(dtf.format(LocalDateTime.now())).get());
			orderDetail.setProduct(productService.getProductById(GlobalData.cart.get(i).getId()).get());
			orderDetail.setQuantity(GlobalData.quantity.get(i));

			orderDetailService.updateOrderDetail(orderDetail);

		}

		return "redirect:/";
	}
}
