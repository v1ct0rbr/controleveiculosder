package br.gov.pb.der.controleveiculosder.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import br.gov.pb.der.controleveiculosder.components.AuthProviderService;
import br.gov.pb.der.controleveiculosder.controller.menu.VeiculoController;
import br.gov.pb.der.controleveiculosder.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebMVCSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private AccessDeniedHandler accessDeniedHandler;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	private AuthProviderService authprovider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	public WebMVCSecurity() {
		super();
	}

	@Override
	public void configure(final WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.requiresChannel().antMatchers("/publico/**").requiresInsecure();
		http.authorizeRequests().antMatchers("/public/**", "/assets/**", "/webjars/**", "/login", "/logout", "/error")
				.permitAll().antMatchers("/menu/veiculos/listar").hasAnyAuthority("CVD_LEITURA", "CVD")
				.antMatchers("/", "/menu/**", "/comum/**").hasAnyAuthority("CVD").and().formLogin().loginPage("/login")
				.defaultSuccessUrl(VeiculoController.URL + "/listar").usernameParameter("username")
				.passwordParameter("password").and().logout().logoutSuccessUrl("/login?logout").and()
				.exceptionHandling().accessDeniedHandler(accessDeniedHandler).accessDeniedPage("/error/403").and()
				.csrf().disable();

	}

	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		auth.authenticationProvider(authprovider);
	}

	@Bean(name = "passwordEncoder")
	public final static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
