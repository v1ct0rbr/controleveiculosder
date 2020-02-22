package br.gov.pb.der.controleveiculosder.controller.menu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

	@GetMapping("/login")
	public String login() {
		return "/public/login";
	}

}