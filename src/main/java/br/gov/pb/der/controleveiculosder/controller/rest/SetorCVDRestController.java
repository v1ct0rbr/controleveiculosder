package br.gov.pb.der.controleveiculosder.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.service.SetorCVDService;
import br.gov.pb.der.controleveiculosder.view.SetorView;

@RestController
@RequestMapping("/menu/rest/setores")
public class SetorCVDRestController {

	@Autowired
	private SetorCVDService setorService;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	private List<SetorView> getEquipamentos(@RequestParam(value = "term") String descricao) {
		List<SetorView> setores = new ArrayList<SetorView>();
		try {
			Integer idSec = Integer.parseInt(descricao);
			setores = setorService.listarTodosPorSecretaria(idSec);
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return null;
		}
		return setores;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String salvarViaAjax(@RequestBody SetorView view) {

		SetorCVD setor = null;
		try {
			setor = setorService.convertViewToSetor(view);
			setorService.salvar(setor);
		} catch (RuntimeException e) {
			return e.getMessage();
		}
		return "sucesso";

	}
}
