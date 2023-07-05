package com.edu.hutech.major.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.hutech.major.dto.UserDTO;
import com.edu.hutech.major.global.GlobalData;
import com.edu.hutech.major.model.Role;
import com.edu.hutech.major.model.User;
import com.edu.hutech.major.service.CategoryService;
import com.edu.hutech.major.service.ProductService;
import com.edu.hutech.major.service.RoleService;
import com.edu.hutech.major.service.UserService;

@Controller
public class HomeController {

	public static final int PRODUCT_MAX_PAGE_SIZE = 8;

	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ProductService productService;

	@GetMapping({ "/", "/{locale:en|vi}", "/home" })
	public String home(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("products", productService.getAllProduct());
		return "index_2";
	} // index

	@GetMapping({ "/user/add", "/{locale:en|vi}/user/add" })
	public String updateUser(Model model) {
		UserDTO currentUser = new UserDTO();
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails && ((UserDetails) principal).getUsername() != null) {
			String currentUsername = ((UserDetails) principal).getUsername();
			User user = userService.getUserByEmail(currentUsername).get();
			currentUser.setId(user.getId());
			currentUser.setEmail(user.getEmail());
			currentUser.setPassword("");
			currentUser.setFirstName(user.getFirstName());
			currentUser.setLastName(user.getLastName());
			List<Integer> roleIds = new ArrayList<>();
			for (Role item : user.getRoles()) {
				roleIds.add(item.getId());
			}
			currentUser.setRoleIds(roleIds);
		} // get current User runtime

		model.addAttribute("userDTO", currentUser);
		return "userRoleAdd";
	}

	@PostMapping({ "/user/add", "/{locale:en|vi}/user/add" })
	public String postUserAdd(@ModelAttribute("userDTO") UserDTO userDTO) { // convert dto > entity
		User user = new User();
		user.setId(userDTO.getId());
		user.setEmail(userDTO.getEmail());
		user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		List<Role> roles = userService.getUserById(user.getId()).get().getRoles();
		user.setRoles(roles);

		userService.updateUser(user);
		return "redirect:/";
	}

	@GetMapping({ "/shop", "/{locale:en|vi}/shop" })
	public String shop(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("pageSize",
				Math.ceil(productService.getAllProduct().size() / (double) PRODUCT_MAX_PAGE_SIZE));
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct());
		return "shop_2";
	} // view All Products

	@GetMapping({ "/shop/category/{id}", "/{locale:en|vi}/shop/category/{id}" })
	public String shopByCat(@PathVariable int id, Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("pageSize",
				Math.ceil(productService.getAllProduct().size() / (double) PRODUCT_MAX_PAGE_SIZE));
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductByCategoryId(id));
		return "shop_2";
	} // view Products By Category

	@GetMapping({ "/shop/viewproduct/{id}", "/{locale:en|vi}/shop/viewproduct/{id}" })
	public String viewProduct(@PathVariable long id, Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("product", productService.getProductById(id).get());
		return "single-product_2";
	} // view product Details

	@PostMapping({ "/shop/searchproduct", "/{locale:en|vi}/shop/searchproduct" })
	public String searchProduct(@RequestParam String name, Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("pageSize",
				Math.ceil(productService.getAllProduct().size() / (double) PRODUCT_MAX_PAGE_SIZE));
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProduct().stream().filter(p -> p.getName().contains(name))
				.collect(Collectors.toList()));
		return "shop_2";
	}

	@GetMapping({ "/shop/sortASC", "/{locale:en|vi}/shop/sortASC" })
	public String sortASCProduct(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("pageSize",
				Math.ceil(productService.getAllProduct().size() / (double) PRODUCT_MAX_PAGE_SIZE));
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductBySortASC());
		return "shop_2";
	}

	@GetMapping({ "/shop/sortDESC", "/{locale:en|vi}/shop/sortDESC" })
	public String sortDESCProduct(Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("pageSize",
				Math.ceil(productService.getAllProduct().size() / (double) PRODUCT_MAX_PAGE_SIZE));
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getAllProductBySortDESC());
		return "shop_2";
	}

	@GetMapping({ "/shop/pagination/{pageNumber}", "/{locale:en|vi}/shop/pagination/{pageNumber}" })
	public String pageProduct(@PathVariable int pageNumber, Model model) {
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("pageSize",
				Math.ceil(productService.getAllProduct().size() / (double) PRODUCT_MAX_PAGE_SIZE));
		model.addAttribute("categories", categoryService.getAllCategory());
		model.addAttribute("products", productService.getProductByPagination(pageNumber - 1));
		return "shop_2";
	}
}
