package br.gov.pb.der.controleveiculosder.controller;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import br.gov.pb.der.controleveiculosder.utils.Parametros;

@SuppressWarnings("rawtypes")
@Configuration

@Controller
@ControllerAdvice
@RequestMapping("/error")
public class MyErrorController extends MyAbstractController implements ErrorViewResolver {

	public static final String VIEW = Parametros.PATH_PUBLIC + "/error";
	public static final String URL = "/error";

	public static final int ERROR_403 = 403;
	public static final int ERROR_404 = 404;
	public static final int ERROR_500 = 500;

	private static final String ERRO_TITULO = "erro_titulo";
	private static final String ERRO_DESCRICAO = "erro_descricao";
	private static final String ERRO_CLASS_APPEND = "class_append";
	private static final String ERRO_NUMERO = "erro_numero";

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR)
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
		// Use the request or status to optionally return a ModelAndView

//		for (Map.Entry<String, Object> entry : model.entrySet()) {
//			System.out.println(entry.getKey() + "/" + entry.getValue());
//
//		}

		ModelAndView mv = new ModelAndView(VIEW);
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

		addInfoErrorMV(mv, status.value());
		if (throwable != null) {
			StringWriter errors = new StringWriter();
			throwable.printStackTrace(new PrintWriter(errors));
			mv.addObject("detalhe_tecnico", errors.toString());
		}
		return mv;

	}

	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public ModelAndView resolveErrorView(@PathVariable("id") int httpErrorCode, HttpServletRequest request) {
		// Use the request or status to optionally return a ModelAndView
		ModelAndView mv = new ModelAndView(VIEW);

		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		addInfoErrorMV(mv, httpErrorCode);
		if (throwable != null) {
			StringWriter errors = new StringWriter();
			throwable.printStackTrace(new PrintWriter(errors));
			mv.addObject("detalhe_tecnico", errors.toString());
		}
		return mv;
	}

//	@RequestMapping(value = "/notificacao", method = RequestMethod.POST)
	public void addInfoErrorMV(ModelAndView mv, int httpErrorCode) {
		mv.addObject(ERRO_NUMERO, httpErrorCode);
		switch (httpErrorCode) {
		case ERROR_403:
			mv.addObject(ERRO_CLASS_APPEND, "text-red");
			mv.addObject(ERRO_TITULO, "Página ou recurso proibido");
			mv.addObject(ERRO_DESCRICAO, "Você não tem permissão para acessar a página solicitada");

			break;
		case ERROR_404:
			mv.addObject(ERRO_CLASS_APPEND, "text-yellow");
			mv.addObject(ERRO_TITULO, "Página não encontrada");
			mv.addObject(ERRO_DESCRICAO, "Nós não encontramos a página que você está procurando");

			break;
		case ERROR_500:
			mv.addObject(ERRO_TITULO, "Erro interno");
			mv.addObject(ERRO_DESCRICAO, " Erro interno do sistema. Estamos tentando identificar o problema");
			mv.addObject(ERRO_CLASS_APPEND, "text-red");
			break;
		default:
			mv.addObject(ERRO_TITULO, "Erro desconhecido");
			mv.addObject(ERRO_DESCRICAO, "Erro desconhecido");
			mv.addObject(ERRO_CLASS_APPEND, "text-yellow");
			break;
		}
	}

	@Override
	public boolean hasValidData(Object object, Errors erros) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCurrURL() {
		// TODO Auto-generated method stub
		return null;
	}

}