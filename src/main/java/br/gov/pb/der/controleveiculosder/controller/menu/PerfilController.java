package br.gov.pb.der.controleveiculosder.controller.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.gov.pb.der.controleveiculosder.controller.MyAbstractController;
import br.gov.pb.der.controleveiculosder.security.CustomUserDetailsService;
import br.gov.pb.der.controleveiculosder.utils.Parametros;
import br.gov.pb.der.controleveiculosder.view.AlterarPerfil;

@Controller
@RequestMapping(value = { "/comum/alterar_senha" })
public class PerfilController extends MyAbstractController<AlterarPerfil> {

	private static final String VIEW = Parametros.PATH_COMUM + "/perfil";
	public static final String URL = "/comum/alterar_senha";

	@Autowired
	private CustomUserDetailsService userService;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder encoder;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(VIEW);
		// AterarPerfil
		mv.addObject("alterarSenha", new AlterarPerfil());

		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated @ModelAttribute("alterarSenha") AlterarPerfil alterarSenha, Errors erros,
			RedirectAttributes attributes) {
		hasValidData(alterarSenha, erros);
		if (erros.hasErrors()) {
			return VIEW;
		}
		try {
			getLoggedUser().setPassword(encoder.encode(alterarSenha.getPassword1()));
			userService.salvar2(getLoggedUser());
			attributes.addFlashAttribute("mensagem", "Alterações salvas com sucesso!");
			return "redirect:/login?logout";
		} catch (IllegalArgumentException e) {
			return VIEW;
		}
	}

	@Override
	@ModelAttribute
	public String getURL() {
		// TODO Auto-generated method stub
		return URL;
	}

	@Override
	public boolean hasValidData(AlterarPerfil object, Errors erros) {
		boolean semErros = true;
		if (!encoder.matches(object.getSenhaAtual(), getLoggedUser().getPassword())) {
			erros.rejectValue("senhaAtual", "sem_codigo", "Sua senha atual é inválida");
			semErros = false;
		}
		if (!object.getPassword1().equals(object.getPassword2())) {
			erros.rejectValue("password1", "sem_codigo", "AS senhas não coincidem");
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
}
