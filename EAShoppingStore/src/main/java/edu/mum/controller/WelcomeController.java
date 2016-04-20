package edu.mum.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.mum.domain.Authority;
import edu.mum.service.WelcomeService;

@Controller
public class WelcomeController {
	
	@Autowired
	private WelcomeService welcomeService;
	
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcomePage(Model model, Principal principal){
		Authority authority = welcomeService.getRole(principal.getName());
		
		model.addAttribute("username", principal.getName());
		/*if(authority.getAuthority().equals("ROLE_OWNER"))
			return "redirect:/owner/ownerHome";
		else
			return "redirect:/doctorHome";*/
		return "redirect:/product/showProducts";
	}
}