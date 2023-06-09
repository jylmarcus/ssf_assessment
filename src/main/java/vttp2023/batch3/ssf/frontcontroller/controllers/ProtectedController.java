package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/protected")
public class ProtectedController {

	@GetMapping
	public String protectedIndex(Model model) {
		return "view1";
	}

	// TODO Task 5
	// Write a controller to protect resources rooted under /protected
}
