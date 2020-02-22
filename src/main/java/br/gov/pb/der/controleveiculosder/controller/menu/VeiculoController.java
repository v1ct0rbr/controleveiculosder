package br.gov.pb.der.controleveiculosder.controller.menu;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pb.der.controleveiculosder.controller.MyAbstractController;
import br.gov.pb.der.controleveiculosder.filtros.VeiculoFiltro;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.model.TipoVeiculo;
import br.gov.pb.der.controleveiculosder.model.Veiculo;
import br.gov.pb.der.controleveiculosder.service.SecretariaService;
import br.gov.pb.der.controleveiculosder.service.TipoVeiculoService;
import br.gov.pb.der.controleveiculosder.service.VeiculoService;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.utils.PageWrapper;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Controller
@RequestMapping(value = { "/menu/veiculos" })
public class VeiculoController extends MyAbstractController<Veiculo> {

	private static final String VIEW_CADASTRO = Parametros.PATH_MENU + "/VeiculoCadastro";
	private static final String VIEW_LISTAR = Parametros.PATH_MENU + "/VeiculosListar";
	public static final String URL = "/menu/veiculos";

	@Autowired
	private VeiculoService service;
	@Autowired
	private SecretariaService secretariaService;

	@Autowired
	private TipoVeiculoService tipoService;

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView editar(@PathVariable("id") Veiculo veiculo) {
		ModelAndView mv = new ModelAndView(VIEW_CADASTRO);
		if (veiculo == null) {
			mv.addObject("veiculo", new Veiculo());
		} else
			mv.addObject("veiculo", veiculo);
		return mv;
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public ModelAndView listar(@Valid @ModelAttribute("filtro") VeiculoFiltro filtro, Pageable pageable,
			BindingResult erros) {
		filtro.setPaginar(true);

		PageWrapper<Veiculo> pageWrapper = null;
		ModelAndView mv = new ModelAndView(VIEW_LISTAR);
		if (erros.hasErrors()) {
			pageWrapper = new PageWrapper<Veiculo>(URL);
			filtro = new VeiculoFiltro();
		} else {
			Page<Veiculo> page = service.filtrar(filtro, pageable);
			pageWrapper = new PageWrapper<Veiculo>(page, URL);
		}

		mv.addObject("page", pageWrapper);
		mv.addObject("adesivo", filtro.getAdesivo());
		mv.addObject("placa", filtro.getPlaca());
		mv.addObject("modelo", filtro.getModelo());
		mv.addObject("funcionarioNome", filtro.getFuncionarioNome());
		mv.addObject("secretariaId", filtro.getSecretariaId());
		mv.addObject("tipoveiculoId", filtro.getTipoveiculoId());
		mv.addObject("setorId", filtro.getSetorId());
		mv.addObject("sortBy", filtro.getSortBy());
		mv.addObject("sortByMethod", filtro.getSortByMethod());
		mv.addObject("filtro", filtro);
		return mv;

	}

	@RequestMapping(value = { "/novo" }, method = RequestMethod.GET)
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(VIEW_CADASTRO);
		mv.addObject("veiculo", new Veiculo());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Valid @ModelAttribute("veiculo") Veiculo veiculo, Errors erros,
			RedirectAttributes attributes) {

		if (erros.hasErrors() || !hasValidData(veiculo, erros)) {
			return VIEW_CADASTRO;
		}
		try {
			service.salvar(veiculo);
			attributes.addFlashAttribute("mensagem", "Salvo com sucesso");
			return "redirect:" + URL + "/" + veiculo.getId();
		} catch (DataIntegrityViolationException e) {
			// TODO Auto-generated catch block
			erros.reject("sem_codigo", "Erro de violação de integridade. (" + e.getMessage() + ")");

		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			erros.reject("sem_codigo", "Erro na cópia dos dados.");
		}
		return VIEW_CADASTRO;
	}

	@PostMapping(value = "/remover/{codigo}")
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes, HttpServletRequest request) {
		try {
			service.excluir(codigo);
			attributes.addFlashAttribute("mensagem", "Veículo excluído com sucesso!");
		} catch (DataIntegrityViolationException e) {
			attributes.addFlashAttribute("error", "Veículo não pode ser excluído");
			attributes.addFlashAttribute("error_details", e.getRootCause().getMessage());
		}
		return redirectToLastURL(request);

	}

	@ModelAttribute("todasSecretarias")
	public List<Secretaria> todasSecretaria() {
		return secretariaService.listar();
	}

	@ModelAttribute("todosTiposVeiculos")
	public List<TipoVeiculo> todosTiposVeiculos() {
		return tipoService.listar();
	}

	@Override
	public boolean hasValidData(Veiculo object, Errors erros) {
		// TODO Auto-generated method stub
		if (!Funcoes.isValidIntegerParameter(object.getFuncionario().getSetor().getId())) {
			erros.rejectValue("funcionario.setor.id", "sem_codigo", "Setor deve ser fornecido corretamente");
		}

		return service.isValid(object, erros);
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

}
