package br.gov.pb.der.controleveiculosder.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pb.der.controleveiculosder.service.FuncionarioService;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.FuncionarioView;
import br.gov.pb.der.controleveiculosder.view.FuncionarioView2;

@RestController
@RequestMapping("/menu/rest/funcionarios")
public class FuncionarioRestController {

	@Autowired
	private FuncionarioService service;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	private List<FuncionarioView> getFuncionarios(@RequestParam(value = "nome") String descricao,
			@RequestParam(value = "id") String idFuncionario) {
		List<FuncionarioView> funcionarios = new ArrayList<FuncionarioView>();
		Long id = null;
		try {
			id = Long.parseLong(Funcoes.isValidStringValue(idFuncionario) ? idFuncionario : "0");
		} catch (NumberFormatException e) {
			return null;
		}
		funcionarios = service.listByNome(descricao, id);
		return funcionarios;
	}

	@RequestMapping(value = "/getbyid", method = RequestMethod.GET)
	private FuncionarioView2 getFuncionariobyId(@RequestParam(value = "id") String idFuncionario) {
		FuncionarioView2 funcionario;
		Long id = null;
		try {
			id = Long.parseLong(Funcoes.isValidStringValue(idFuncionario) ? idFuncionario : "0");
		} catch (NumberFormatException e) {
			return null;
		}
		funcionario = service.findViewById(id);
		return funcionario;
	}
}
