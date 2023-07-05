package com.edu.hutech.major.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.hutech.major.dto.OrderDTO;
import com.edu.hutech.major.global.GlobalData;
import com.edu.hutech.major.model.Product;
import com.edu.hutech.major.service.ProductService;

@Controller
public class CartController {
	@Autowired
	ProductService productService;

	@GetMapping("/cart")
	public String cartGet(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", GlobalData.cart);
		model.addAttribute("quantity", GlobalData.quantity);
		return "cart_2";
	}// page cart

	@GetMapping("/addToCartDetail/{id}")
	public String addToCartDetail(@PathVariable int id, @RequestParam int quantity) {
		GlobalData.cart.add(productService.getProductById(id).get());
		GlobalData.quantity.add(quantity);
		return "redirect:/shop";
	}// click add from page viewProduct

	@GetMapping("/addToCartShop/{id}")
	public String addToCartShop(@PathVariable int id) {
		GlobalData.cart.add(productService.getProductById(id).get());
		GlobalData.quantity.add(1);
		return "redirect:/shop";
	}// click add from page viewProduct

	@GetMapping("/cart/removeItem/{index}")
	public String cartItemRemove(@PathVariable int index) {
		GlobalData.cart.remove(index);
		GlobalData.quantity.remove(index);
		return "redirect:/cart";
	} // delete 1 product

	@GetMapping("/checkout")
	public String checkout(Model model) {
		model.addAttribute("orderDTO", new OrderDTO());
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
		model.addAttribute("cart", GlobalData.cart);
		model.addAttribute("quantity", GlobalData.quantity);
		return "checkout";
	} // checkout totalPrice
}
