package com.example.mysecuritypage.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mysecuritypage.model.Auth;
import com.example.mysecuritypage.model.User;
import com.example.mysecuritypage.repository.AuthRepository;
import com.example.mysecuritypage.repository.UserRepository;
import com.example.mysecuritypage.security.PasswordCrypt;

@Controller
public class PageController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private PasswordCrypt passCrypt;
	
	@GetMapping("/")
	public @ResponseBody String pageIndex() {
		return "This is my index page... Wellcome!";
	}
	
	@GetMapping(value = {"/dashboard", "/dashboard/"})
	public String pageDashboard(Model model)
	{
		
		Authentication autenticado = SecurityContextHolder.getContext().getAuthentication();
		User user = userRepository.findByEmail(autenticado.getName());
		model.addAttribute("user", user);
		
		return "/dashboard/usuario/profile";
	}

	@GetMapping(value = {"/login", "/login/"})
	public String pageLogin() {
		return "/login/login-template";
	}
	
	@GetMapping(value = {"/register", "/register/"})
	public String pageRegister() {
		return "/login/registro-template";
	}
	
	
	@PostMapping(value = {"/register/user", "/register/user/"})
	public String userRegister(User user) {
		
		if(!authRepository.existsByRoleName("ROLE_User")) {
			authRepository.save(new Auth("ROLE_User"));
		}
		
		Auth userRole = authRepository.findByRoleName("ROLE_User");
		user.setPasswd(passCrypt.encode(user.getPasswd()));
		user.setEnable(true);
		user.setAuths(Arrays.asList(userRole));
		
		userRepository.save(user);
		
		return "redirect:/login";
	}
}
