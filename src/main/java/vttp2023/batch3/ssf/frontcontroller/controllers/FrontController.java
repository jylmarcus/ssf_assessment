package vttp2023.batch3.ssf.frontcontroller.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp2023.batch3.ssf.frontcontroller.model.Captcha;
import vttp2023.batch3.ssf.frontcontroller.model.LoginForm;
import vttp2023.batch3.ssf.frontcontroller.services.AuthenticationService;

@Controller
public class FrontController {

	@Autowired
	AuthenticationService service;

	private Map<String, Integer> loginAttempts = new HashMap<String, Integer>();

	@RequestMapping("/")
	public String index(Model model, HttpSession session){
		session.invalidate();
		model.addAttribute("loginForm", new LoginForm());
		return "view0";
	}

	@PostMapping(path="/login", consumes= MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, @ModelAttribute("captcha") Captcha modelCaptcha, BindingResult bindingResult, Model model, HttpSession session, RedirectAttributes redirectAttributes) throws Exception {
		if(bindingResult.hasErrors()) {
			return "view0";
		}

		loginAttempts.putIfAbsent(loginForm.getUsername(), 0);
		System.out.println("Login Attempts: " + loginAttempts.get(loginForm.getUsername()));
		ResponseEntity<String> resp = service.authenticate(loginForm.getUsername(), loginForm.getPassword());

		if(service.isLocked(loginForm.getUsername())) {
			model.addAttribute("username", loginForm.getUsername());
			return "view2";
		}

		if(loginAttempts.get(loginForm.getUsername()) > 0) {
			int captchaSolution = ((Captcha) session.getAttribute("captcha")).getSolution();
			int inputSolution = modelCaptcha.getInputSolution();

			if(captchaSolution != inputSolution) {
				loginAttempts.computeIfPresent(loginForm.getUsername(), (k, v) -> v += 1);
				model.addAttribute("captchaError", "Incorrect captcha");
				Captcha captcha = new Captcha();
				model.addAttribute("captcha", captcha);
				session.setAttribute("captcha", captcha);
				return "view0";
			}
		}

		if(resp.getStatusCode().is4xxClientError() && (loginAttempts.get(loginForm.getUsername()) > 3)) {
			service.disableUser(loginForm.getUsername());
			model.addAttribute("username", loginForm.getUsername());
			return "view2";
		}

		if(resp.getStatusCode().is4xxClientError()) {
			loginAttempts.computeIfPresent(loginForm.getUsername(), (k, v) -> v += 1);
			model.addAttribute("loginError", "Login failed");
			Captcha captcha = new Captcha();
			model.addAttribute("captcha", captcha);
			session.setAttribute("captcha", captcha);
			return "view0";
		}

		loginAttempts.computeIfPresent(loginForm.getUsername(), (k, v) -> v = 0);
		service.authenticateUser(loginForm.getUsername());
		redirectAttributes.addAttribute("username", loginForm.getUsername());
		return "redirect:/protected";
	}

	// TODO: Task 6
	
}
