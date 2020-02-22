package br.gov.pb.der.controleveiculosder.controller.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pb.der.controleveiculosder.controller.MyAbstractController;
import br.gov.pb.der.controleveiculosder.filtros.SetorFiltro;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.service.SecretariaService;
import br.gov.pb.der.controleveiculosder.service.SetorCVDService;
import br.gov.pb.der.controleveiculosder.utils.PageWrapper;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Controller
@RequestMapping("/menu/dadosauxiliares/setores")
public class SetorController extends MyAbstractController<SetorCVD> {

	private static final String VIEW_LISTAR = Parametros.PATH_MENU + "/setor/SetorListar";
	private static final String VIEW_CADASTRO = Parametros.PATH_MENU + "/setor/SetorCadastro";
	public static final String URL = "/menu/dadosauxiliares/setores";
//	public static final String URL_MINI = "/setores";

	@Autowired
	private SetorCVDService setorService;

	@Autowired
	private SecretariaService secretariaService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(VIEW_CADASTRO);

		mv.addObject("setor", new SetorCVD());
		return mv;
	}

	@RequestMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") SetorCVD setor) {
		ModelAndView mv = new ModelAndView(VIEW_CADASTRO);

		if (setor == null) {
			mv.addObject("setor", new SetorCVD());
		} else
			mv.addObject("setor", setor);
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET)
	ModelAndView pesquisar(@Validated @ModelAttribute("filtro") SetorFiltro filtro, Pageable pageable, Errors erros) {

		PageWrapper<SetorCVD> pageWrapper = null;

		if (erros.hasErrors()) {
			pageWrapper = new PageWrapper<SetorCVD>(URL);
		} else {
			Page<SetorCVD> page = setorService.filtrar(filtro, pageable);
			pageWrapper = new PageWrapper<SetorCVD>(page, URL);
		}

		ModelAndView mv = new ModelAndView(VIEW_LISTAR);
		mv.addObject("page", pageWrapper);

		mv.addObject("nome", filtro.getNome());
		mv.addObject("codigo", filtro.getCodigo());
		mv.addObject("secretariaId", filtro.getSecretariaId());
		mv.addObject("sortBy", filtro.getSortBy());
		mv.addObject("sortByMethod", filtro.getSortByMethod());
		return mv;
	}

	@PostMapping
	public String salvar(@ModelAttribute("setor") @Validated final SetorCVD setor, Errors erros,
			RedirectAttributes attributes) {

		if (erros.hasErrors() || !hasValidData(setor, erros)) {
			return VIEW_CADASTRO;
		}
		try {
			setorService.salvar(setor);
			attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
			return "redirect:" + URL + "/" + setor.getId();
		} catch (RuntimeException e) {
			erros.reject("sem_codigo", "Erro encontrado: " + e.getMessage());
			return VIEW_CADASTRO;
		}
	}

	@PostMapping(value = "/remover/{codigo}")
	public String excluir(@PathVariable Integer codigo, RedirectAttributes attributes, HttpServletRequest request) {
		try {
			setorService.excluir(codigo);
			attributes.addFlashAttribute("mensagem", "SetorCVD excluído com sucesso!");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("error", "SetorCVD não pode ser excluído");
			attributes.addFlashAttribute("error_details", e.getRootCause().getMessage());

		}
		return redirectToLastURL(request);

	}

	@Override
	public boolean hasValidData(SetorCVD setor, Errors erros) {
		// TODO Auto-generated method stub
		boolean semErros = true;

		if (setor.isSecretariaValida()) {
			boolean setorAlreadyExists = setorService.setorExists(setor);
			boolean secretariaExists = secretariaService.existsById(setor.getSecretaria().getId());
			if (setorAlreadyExists) {
				erros.rejectValue("nome", "sem_codigo", "já existe setor de mesmo nome!");
				semErros = false;
			}
			if (!secretariaExists) {
				erros.rejectValue("secretaria.id", "sem_codigo", "Secretaria fornecida não existe ou foi excluída");
			}

		} else {
			erros.rejectValue("secretaria.id", "sem_codigo", "Secretaria / orgão precisa ser fornecido!");
			semErros = false;
		}

		return semErros;
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

}
