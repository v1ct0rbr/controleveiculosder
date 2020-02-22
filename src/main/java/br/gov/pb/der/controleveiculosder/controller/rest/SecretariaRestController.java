package br.gov.pb.der.controleveiculosder.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pb.der.controleveiculosder.service.SecretariaService;
import br.gov.pb.der.controleveiculosder.view.SecretariaView;

@RestController
@RequestMapping("/menu/rest/secretarias")
public class SecretariaRestController {

	@Autowired
	private SecretariaService service;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	private List<SecretariaView> getSecretarias() {
		List<SecretariaView> secretarias = new ArrayList<SecretariaView>();
		try {

			secretarias = service.listarView();
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return null;
		}
		return secretarias;
	}

}
