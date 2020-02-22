package br.gov.pb.der.controleveiculosder.service;

import java.security.Principal;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public abstract class MyAbstractService {

	public Principal getLoggedUser() {
		return (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	}

	public String getCurrentUrlHttp() {
		String url = "";
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath();
		builder.scheme("http");
		url = builder.build().toUriString();

		return url;
	}

}
