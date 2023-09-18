package com.manoj.mobilesalesandcustomer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.manoj.mobilesalesandcustomer.helper.Message;
import com.manoj.mobilesalesandcustomer.model.User;
import com.manoj.mobilesalesandcustomer.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private com.manoj.mobilesalesandcustomer.config.UserRedirectWrtLogin redirect;

	@GetMapping("/")
	public String homeStart(Model model) {
		model.addAttribute("title", "Mobile Sales and Customer Management");
		return "home";
	}

	@GetMapping("/home")
	public String homePage(Model model) {
		model.addAttribute("title", "Home-MSCM");
		return "home";
	}

	@GetMapping("/signup")
	public String signUp(Model model) {
		if (redirect.isAuthenticated()) {
			return "user/index";
		} else {
			model.addAttribute("title", "SignUp-MSCM");
			model.addAttribute("user", new User());
			return "signup";
		}

	}

	@GetMapping("/signin")
	public String signIn(Model model) {
		if (redirect.isAuthenticated()) {
			return "user/index";
		} else {
			model.addAttribute("title", "SignIn-MSCM");
			return "signin";
		}
	}

	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About-MSCM");
		return "about";
	}

	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute("user") User user,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {
		try {
			if (!agreement) {
				System.out.println("You have not agreed the terms and conditions");
				throw new Exception("You have not agreed the terms and conditions");
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			this.userRepository.save(user);
			model.addAttribute("user", new User());
			session.setAttribute("message", new Message("Successfully Registered!!", "alert-success"));
			return "signup";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something Went Wrong! " + e.getMessage(), "alert-danger"));
			return "signup";
		}

	}
}
