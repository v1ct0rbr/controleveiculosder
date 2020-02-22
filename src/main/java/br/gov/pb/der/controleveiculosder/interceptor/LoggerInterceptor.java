package br.gov.pb.der.controleveiculosder.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter implements MyHandlerInterceptor {

	static Logger logger = Logger.getLogger(LoggerInterceptor.class);

	static {
		BasicConfigurator.configure();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		logger.info("Before handling the request");

		verificarSessaoInvalida();
		return super.preHandle(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		logger.info("After handling the request");
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		logger.info("After rendering the view");
		super.afterCompletion(request, response, handler, ex);
	}

	public boolean verificarSessaoInvalida() throws IOException {
		try {
			Object loggedUser = null;
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			loggedUser = auth != null ? auth.getPrincipal() : null;
			if (loggedUser == null) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}