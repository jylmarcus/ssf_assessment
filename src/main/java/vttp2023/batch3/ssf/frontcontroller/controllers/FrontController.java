package vttp2023.batch3.ssf.frontcontroller.controllers;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.model.LoginForm;

@Controller
public class FrontController {

	@RequestMapping("/")
	public String index(Model model){
		model.addAttribute("loginForm", new LoginForm());
		return "view0";
	}
	// TODO: Task 2, Task 3, Task 4, Task 6

	@PostMapping(path="/login", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			return "view0";
		}
		return "view1";
	}
	
}
