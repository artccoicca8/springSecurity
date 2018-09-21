package com.bolsadeideas.springboot.app.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam (value="logout",required=false) String logout ,
			@RequestParam (value="error",required=false) String error ,
			Model model,Principal principal , RedirectAttributes attributes) {
		
		/**
		 * Se valida que ya se haya iniciado una session 
		 * */
		
		if (principal!=null) {
			
			attributes.addFlashAttribute("info", "ya iniciaste session prro");
			
			return "redirect:/"; 
		}
		
		/**
		 * Se valida que el usuario o contraseña esten correctos 
		 * */
		
		if (error!=null) {
			model.addAttribute("error", "Error en el Usuario o la contraseña");
		}
		 
		/**
		 * se cierra la session si ental caso se evia esa horden 
		 * */
		if (logout!=null) {
			model.addAttribute("success", "Ha cerrado la session con exito  ");
		}
		 
		
		return "login";
	}
}
