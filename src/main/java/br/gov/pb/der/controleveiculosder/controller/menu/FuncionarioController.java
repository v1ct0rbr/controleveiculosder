package br.gov.pb.der.controleveiculosder.controller.menu;

import java.util.List;

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
import br.gov.pb.der.controleveiculosder.filtros.FuncionarioFiltro;
import br.gov.pb.der.controleveiculosder.model.Funcionario;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.service.FuncionarioService;
import br.gov.pb.der.controleveiculosder.service.SecretariaService;
import br.gov.pb.der.controleveiculosder.service.SetorService;
import br.gov.pb.der.controleveiculosder.utils.PageWrapper;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Controller
@RequestMapping("/menu/dadosauxiliares/funcionarios")
public class FuncionarioController extends MyAbstractController<Funcionario> {

	private static final String VIEW_LISTAR = Parametros.PATH_MENU + "/funcionario/FuncionariosListar";
	private static final String VIEW_CADASTRO = Parametros.PATH_MENU + "/funcionario/FuncionarioCadastro";
	public static final String URL = "/menu/dadosauxiliares/funcionarios";
//	public static final String URL_MINI = "/setores";

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private SecretariaService secretariaService;

	@Autowired
	private SetorService setorService;

	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(VIEW_CADASTRO);

		mv.addObject("funcionario", new Funcionario());
		return mv;
	}

	@RequestMapping("{id}")
	public ModelAndView edicao(@PathVariable("id") Funcionario funcionario) {
		ModelAndView mv = new ModelAndView(VIEW_CADASTRO);

		if (funcionario == null) {
			mv.addObject("funcionario", new Funcionario());
		} else
			mv.addObject("funcionario", funcionario);
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET)
	ModelAndView pesquisar(@ModelAttribute("filtro") FuncionarioFiltro filtro, Pageable pageable) {
		Page<Funcionario> page = funcionarioService.filtrar(filtro, pageable);
		PageWrapper<Funcionario> pageWrapper = new PageWrapper<Funcionario>(page, URL);
		ModelAndView mv = new ModelAndView(VIEW_LISTAR);

		mv.addObject("page", pageWrapper);
		mv.addObject("nome", filtro.getNome());
		mv.addObject("setorId", filtro.getSetorId());
		mv.addObject("secretariaId", filtro.getSecretariaId());
		mv.addObject("sortBy", filtro.getSortBy());
		mv.addObject("sortByMethod", filtro.getSortByMethod());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@ModelAttribute("funcionario") @Validated final Funcionario funcionario, Errors erros,
			RedirectAttributes attributes) {

		if (erros.hasErrors() || !hasValidData(funcionario, erros)) {
			return VIEW_CADASTRO;
		}
		try {
			funcionarioService.salvar(funcionario);
			attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
			return "redirect:" + URL + "/" + funcionario.getId();
		} catch (RuntimeException e) {
			erros.reject("sem_codigo", "Erro encontrado: " + e.getMessage());
			return VIEW_CADASTRO;
		}
	}

	@PostMapping(value = "/remover/{codigo}")
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		try {
			funcionarioService.excluir(codigo);
			attributes.addFlashAttribute("mensagem", "Funcionario excluído com sucesso!");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("error", "Funcionario não pode ser excluído");
			attributes.addFlashAttribute("error_details", e.getRootCause().getMessage());

		}
		return "redirect:" + URL;

	}

	@Override
	public boolean hasValidData(Funcionario setor, Errors erros) {
		// TODO Auto-generated method stub
		boolean semErros = true;

		if (setor.hasSetorValido()) {
			if (!setorService.existsById(setor.getSetor().getId())) {
				erros.rejectValue("setor.id", "sem_codigo", "Setor informado não foi encontrado");
				semErros = false;
			}
		} else {
			erros.rejectValue("setor.id", "sem_codigo", "Informe o setor");
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
