package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
@RequestMapping("/protected")
public class ProtectedController {
	@Autowired
	AuthenticationService service;

	@GetMapping
	public String protectedIndex(@RequestParam(name="username") String username, Model model) {
		if(!service.isAuthenticated(username)) {
			model.addAttribute("loginError", "Login first to access protected resources");
		}
		model.addAttribute("username", username);
		return "view1";
	}

	@GetMapping(path="/logout")
	public String logout(@ModelAttribute("username") String username, Model model){
		service.logoutUser(username);
		return "/";
	}

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected
}
