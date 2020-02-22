package br.gov.pb.der.controleveiculosder.controller.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pb.der.controleveiculosder.controller.MyAbstractController;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.service.SecretariaService;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Controller
@RequestMapping("/menu/dadosauxiliares/secretarias")
public class SecretariaController extends MyAbstractController<Secretaria> {

	private static final String VIEW = Parametros.PATH_MENU + "/Secretarias";
	public static final String URL = "/menu/dadosauxiliares/secretarias";

	@Autowired
	private SecretariaService secretariaService;

	@GetMapping
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(VIEW);

		mv.addObject("secretaria", new Secretaria());
		return mv;
	}

	@GetMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") Secretaria secretaria) {
		ModelAndView mv = new ModelAndView(VIEW);

		if (secretaria == null) {
			mv.addObject("secretaria", new Secretaria());
		} else
			mv.addObject("secretaria", secretaria);
		return mv;
	}

	@PostMapping
	public String salvar(@ModelAttribute("secretaria") @Validated final Secretaria secretaria, Errors erros,
			RedirectAttributes attributes) {

		if (erros.hasErrors() || !hasValidData(secretaria, erros)) {
			return VIEW;
		}
		try {
			secretariaService.salvar(secretaria);
			attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
			return "redirect:" + URL + "/" + secretaria.getId();
		} catch (RuntimeException e) {
			erros.reject("sem_codigo", "Erro encontrado: " + e.getMessage());
			return VIEW;
		}
	}

	@PostMapping(value = "/remover/{codigo}")
	public String excluir(@PathVariable Integer codigo, RedirectAttributes attributes) {
		try {
			secretariaService.excluir(codigo);
			attributes.addFlashAttribute("mensagem", "Secretaria excluída com sucesso!");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("error", "Secretaria não pode ser excluída");
			attributes.addFlashAttribute("error_details", e.getRootCause().getMessage());
		}
		return "redirect:" + URL;
	}

	@Override
	@ModelAttribute("url_base")
	public String getCurrURL() {
		// TODO Auto-generated method stub
		if (URL.startsWith("/"))
			return URL.substring(1);
		else
			return URL;
	}

	@ModelAttribute("todasSecretarias")
	public List<Secretaria> todasSecretaria() {
		return secretariaService.listar();
	}

	@Override
	public boolean hasValidData(Secretaria object, Errors erros) {
		// TODO Auto-generated method stub
		boolean semErros = true;

		boolean secretariaExists = secretariaService.existsByNome(object);

		if (secretariaExists) {
			erros.rejectValue("secretaria.nome", "sem_codigo", "Secretaria com o nome fornecido já existe");
			semErros = false;
		}

		return semErros;
	}

}
