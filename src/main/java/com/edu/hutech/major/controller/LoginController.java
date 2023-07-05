package com.edu.hutech.major.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.edu.hutech.major.dto.UserDTO;
import com.edu.hutech.major.global.GlobalData;
import com.edu.hutech.major.model.Role;
import com.edu.hutech.major.model.User;
import com.edu.hutech.major.repository.RoleRepository;
import com.edu.hutech.major.repository.UserRepository;
import com.edu.hutech.major.service.EmailSenderService;
import com.edu.hutech.major.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private PasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	EmailSenderService emailSenderService;

	@GetMapping("/login")
	public String login() {
		GlobalData.cart.clear();
		return "login";
	}// page login

	@GetMapping("/forgotpassword")
	public String forgotPasswordGet(Model model) {
		model.addAttribute("userDTO", new UserDTO());
		return "forgotpassword";
	}

	@GetMapping("/register")
	public String registerGet(Model model) {
		return "register";
	} // page register

	@PostMapping("/register")
	public String registerPost(@ModelAttribute User userModel, HttpServletRequest request) throws ServletException {
		String password = userModel.getPassword();
		userModel.setPassword(bCryptPasswordEncoder.encode(password));
		List<Role> roles = new ArrayList<>();
		/* roles.add(roleRepository.findById(1).get()); */
		roles.add(roleRepository.findById(2).get());
		userModel.setRoles(roles);
		userRepository.save(userModel);
		request.login(userModel.getEmail(), password);
		return "redirect:/";
	}

	@PostMapping("/forgotpassword")
	public String forgotPasswordPost(@RequestParam String email) {
		User user = userService.getUserByEmail(email).get();
		String newPass = String.valueOf((int) (Math.random() * ((9999 - 1000) + 1)) + 1000);
		user.setPassword(bCryptPasswordEncoder.encode(newPass));
		userService.updateUser(user);
		emailSenderService.sendEmail(email, "Forgot Password", "This is New Password: " + newPass);
		return "redirect:/login";
	}
}
