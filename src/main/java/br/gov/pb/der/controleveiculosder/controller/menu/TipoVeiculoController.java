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
import br.gov.pb.der.controleveiculosder.model.TipoVeiculo;
import br.gov.pb.der.controleveiculosder.service.TipoVeiculoService;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Controller
@RequestMapping("/menu/veiculos/tipoveiculo")
public class TipoVeiculoController extends MyAbstractController<TipoVeiculo> {

	private static final String VIEW = Parametros.PATH_MENU + "/TipoVeiculo";
	public static final String URL = "/menu/veiculos/tipoveiculo";

	@Autowired
	private TipoVeiculoService tipoService;

	@GetMapping
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(VIEW);

		mv.addObject("tipoveiculo", new TipoVeiculo());
		return mv;
	}

	@GetMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") TipoVeiculo tipoveiculo) {
		ModelAndView mv = new ModelAndView(VIEW);

		if (tipoveiculo == null) {
			mv.addObject("tipoveiculo", new TipoVeiculo());
		} else
			mv.addObject("tipoveiculo", tipoveiculo);
		return mv;
	}

	@PostMapping
	public String salvar(@ModelAttribute("tipoveiculo") @Validated final TipoVeiculo tipoveiculo, Errors erros,
			RedirectAttributes attributes) {

		if (erros.hasErrors() || !hasValidData(tipoveiculo, erros)) {
			return VIEW;
		}
		try {
			tipoService.salvar(tipoveiculo);
			attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
			return "redirect:" + URL + "/" + tipoveiculo.getId();
		} catch (RuntimeException e) {
			erros.reject("sem_codigo", "Erro encontrado: " + e.getMessage());
			return VIEW;
		}
	}

	@PostMapping(value = "/remover/{codigo}")
	public String excluir(@PathVariable Integer codigo, RedirectAttributes attributes) {
		try {
			tipoService.excluir(codigo);
			attributes.addFlashAttribute("mensagem", "Tipo excluído com sucesso!");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("error", "Tipo não pode ser excluído");
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

	@ModelAttribute("todosTipoVeiculos")
	public List<TipoVeiculo> todasTipoVeiculo() {
		return tipoService.listar();
	}

	@Override
	public boolean hasValidData(TipoVeiculo object, Errors erros) {
		// TODO Auto-generated method stub
		boolean semErros = true;

		boolean tipoveiculoExists = tipoService.existsByNome(object);

		if (tipoveiculoExists) {
			erros.rejectValue("nome", "sem_codigo", "Tipo com o nome fornecido já existe");
			semErros = false;
		}

		return semErros;
	}

}
