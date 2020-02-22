package br.gov.pb.der.controleveiculosder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.pb.der.controleveiculosder.config.parameters.EmpresaConfig;
import br.gov.pb.der.controleveiculosder.model.User;
import br.gov.pb.der.controleveiculosder.service.EmpresaConfigService;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

public abstract class MyAbstractController<T> {
//
//	@Autowired
//	protected ServicoService servicoService;
//
//	@Autowired
//	protected StatusServicoService statusService;
//
//	@Autowired
//	protected EmprestimoEquipamentoService emprestimoService;
//
//	@Autowired
//	protected ChamadoExternoService chamadoService;
//
//	@Autowired
//	protected ParametroService parametroService;
//
//	@Autowired
//	protected MensagemServicoService mensagemService;

	@Autowired
	private EmpresaConfigService configEmpresa;

	@Autowired
	protected MessageSource messageSource;

	public Map<String, Object> getEmpresaInfo() {
		EmpresaConfig config = new EmpresaConfig();
		try {
			config = configEmpresa.carregarParametros();
		} catch (ConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> parametros = new HashMap<>();

		parametros.put("titulo_relatorio", "Ordem de Serviço");
		parametros.put("empresa_nome", config.getEmpresaNome());
		parametros.put("empresa_setor_nome", config.getEmpresaSetor());
		parametros.put("empresa_localidade", config.getEmpresaLocalidade());
		parametros.put("empresa_endereco", config.getEmpresaEndereco());
		parametros.put("empresa_telefone", config.getEmpresaTelefone());
		parametros.put("empresa_logo", Parametros.PATH_RELATORIOS + "logo-der.png");
		parametros.put("PATH_RELATORIOS", Parametros.PATH_RELATORIOS);
		parametros.put("SUBREPORT_DIR", Parametros.PATH_SUB_REPORT);
		return parametros;
	}

	public Map<String, Object> getEmpresaInfo(String logo) {
		EmpresaConfig config = new EmpresaConfig();
		try {
			config = configEmpresa.carregarParametros();
		} catch (ConfigurationException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> parametros = new HashMap<>();

		parametros.put("titulo_relatorio", "Ordem de Serviço");
		parametros.put("empresa_nome", config.getEmpresaNome());
		parametros.put("empresa_setor_nome", config.getEmpresaSetor());
		parametros.put("empresa_localidade", config.getEmpresaLocalidade());
		parametros.put("empresa_endereco", config.getEmpresaEndereco());
		parametros.put("empresa_telefone", config.getEmpresaTelefone());
		parametros.put("empresa_logo", Parametros.PATH_RELATORIOS + logo);
		parametros.put("PATH_RELATORIOS", Parametros.PATH_RELATORIOS);
		parametros.put("SUBREPORT_DIR", Parametros.PATH_SUB_REPORT);
		return parametros;
	}

	public User getLoggedUser() {
		User user = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object obj = auth != null ? auth.getPrincipal() : null;
		if (obj != null) {
			if (!(obj instanceof String))
				user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		return user;
	}

	public String getURL() {
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequestUri();
		builder.scheme("https");
		builder.replaceQueryParam("someBoolean", false);
		URI newUri = builder.build().toUri();
		return newUri.toString();

	}

	public String getCurrentUrlHttps() {
		String url = "";
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath();
		builder.scheme("https");
		url = builder.build().toUriString().replace("8080", "8443");
		return url;
	}

	public String getCurrentUrlHttp() {
		String url = "";
		ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentContextPath();
		builder.scheme("http");
		url = builder.build().toUriString();

		return url;
	}

	public String redirectToLastURL(HttpServletRequest request) {
		String referer = request.getHeader("Referer");
		return "redirect:" + referer;
	}

	@ModelAttribute("testeSideMenu")
	public boolean teste(HttpServletRequest response) {
		if (response.getCookies() == null)
			return false;
		for (Cookie cookie : response.getCookies()) {
			if (cookie.getName().equals("collapse_sidebar")) {
				if (cookie.getValue().equals("1"))
					return true;
			}
		}
		return false;
	}

	public boolean hasAuthority(String authority) {
		return getLoggedUser().hasAuthority(authority);
	}

	public boolean verificarPermissao(ModelAndView mv, String role) {

		if (!hasAuthority(role)) {

			return false;
		}
		return true;
	}

	public String getMensagem(String codigo_mensagem) {
		return messageSource.getMessage(codigo_mensagem, null, LocaleContextHolder.getLocale());
	}

	public String getMensagemComParametros(String codigo_mensagem, Object[] args) {
		return messageSource.getMessage(codigo_mensagem, args, LocaleContextHolder.getLocale());
	}

	protected String getPrintStackTrace(Throwable throwable) {
		if (throwable != null) {
			StringWriter errors = new StringWriter();
			throwable.printStackTrace(new PrintWriter(errors));
			return errors.toString();
		}
		return null;
	}

	protected Integer getCurrentYear() {
		LocalDate now = LocalDate.now();
		return now.getYear();
	}

	public abstract String getCurrURL();

	public abstract boolean hasValidData(T object, Errors erros);

}
