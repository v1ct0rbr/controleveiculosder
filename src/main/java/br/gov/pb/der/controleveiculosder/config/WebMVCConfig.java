package br.gov.pb.der.controleveiculosder.config;

import java.util.List;
import java.util.Locale;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.ISpringTemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import br.gov.pb.der.controleveiculosder.controller.menu.VeiculoController;
import br.gov.pb.der.controleveiculosder.interceptor.LoggerInterceptor;
import br.gov.pb.der.controleveiculosder.interceptor.MyHandlerInterceptor;
import br.gov.pb.der.controleveiculosder.utils.ArrayUtil;
import br.gov.pb.der.controleveiculosder.utils.PageWrapper;
import br.gov.pb.der.controleveiculosder.utils.Parametros;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;

@Configuration
@EnableWebMvc
@ComponentScan("br.gov.pb.der.controleveiculosder")
/**
 * Java configuration file that is used for Spring MVC and Thymeleaf
 * configurations
 */
public class WebMVCConfig implements WebMvcConfigurer, ApplicationContextAware {

	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	@Bean
	public ViewResolver htmlViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(htmlTemplateResolver()));
		resolver.setContentType("text/html");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(ArrayUtil.array("*.html"));
		return resolver;
	}

	@Bean
	public ViewResolver javascriptViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(javascriptTemplateResolver()));
		resolver.setContentType("application/javascript");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(ArrayUtil.array("*.js"));
		return resolver;
	}

	@Bean
	public ViewResolver cssViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(cssTemplateResolver()));
		resolver.setContentType("text/css");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(ArrayUtil.array("*.css"));
		return resolver;
	}

	@Bean
	public ViewResolver plainViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine(plainTemplateResolver()));
		resolver.setContentType("text/plain");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setViewNames(ArrayUtil.array("*.txt"));
		return resolver;
	}

	private ISpringTemplateEngine templateEngine(ITemplateResolver templateResolver) {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.addDialect(new LayoutDialect(new GroupingStrategy()));
		engine.addDialect(new Java8TimeDialect());
		engine.setTemplateResolver(templateResolver);
		engine.setTemplateEngineMessageSource(messageSource());
		return engine;
	}

	private ITemplateResolver htmlTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/resources/templates/");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}

	private ITemplateResolver javascriptTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/assets/");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.JAVASCRIPT);
		return resolver;
	}

	private ITemplateResolver cssTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/assets/");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.CSS);
		return resolver;
	}

	private ITemplateResolver plainTemplateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("/assets/");
		resolver.setCacheable(false);
		resolver.setTemplateMode(TemplateMode.TEXT);
		return resolver;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages", "classpath:ValidationMessages");
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
		lci.setParamName("lang");
		return lci;
	}

	@Bean
	public MyHandlerInterceptor loggerInterceptor() {
		return new LoggerInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
		registry.addInterceptor(loggerInterceptor());
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName(Parametros.PATH_PUBLIC + "/login");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		registry.addRedirectViewController("/", VeiculoController.URL + "/listar");
	}

//	@SuppressWarnings("deprecation")
//	@Override
//	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
//		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
//		resolver.setFallbackPageable(PageRequest.of(0, PageWrapper.MAX_ITENS_DISPLAY));
//		argumentResolvers.add(resolver);
////		super.addArgumentResolvers(argumentResolvers);
//	}

	@SuppressWarnings("deprecation")
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
		resolver.setFallbackPageable(new PageRequest(0, PageWrapper.MAX_ITENS_DISPLAY));
		argumentResolvers.add(resolver);
//		super.addArgumentResolvers(argumentResolvers);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**", "/assets/**").addResourceLocations("classpath:/resources/",
				"classpath:/static/assets/");
	}

//	@Bean
//	public ClassLoaderTemplateResolver secondaryTemplateResolver() {
//		ClassLoaderTemplateResolver secondaryTemplateResolver = new ClassLoaderTemplateResolver();
//		secondaryTemplateResolver.setPrefix("templates/");
//		secondaryTemplateResolver.setSuffix(".html");
//		secondaryTemplateResolver.setTemplateMode(TemplateMode.HTML);
//		secondaryTemplateResolver.setCharacterEncoding("UTF-8");
//		secondaryTemplateResolver.setOrder(1);
//		secondaryTemplateResolver.setCheckExistence(true);
//
//		return secondaryTemplateResolver;
//	}

//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/", "file:resources/");
//	}

//	@Override
//	@Description("Custom Conversion Service")
//	public void addFormatters(FormatterRegistry registry) {
//		registry.addFormatter(new NameFormatter());
//	}

}
