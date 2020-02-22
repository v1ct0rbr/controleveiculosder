package br.gov.pb.der.controleveiculosder.components;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.gov.pb.der.controleveiculosder.config.WebMVCSecurity;
import br.gov.pb.der.controleveiculosder.model.User;
import br.gov.pb.der.controleveiculosder.security.CustomUserDetailsService;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;

@Component
public class AuthProviderService implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService usuarioService;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder myEncoder;

	// @Autowired
	// @Qualifier("contextSource")
	// DefaultSpringSecurityContextSource contextSource;

	public void setPasswordEncoder(PasswordEncoder encoder) {
		this.myEncoder = encoder;
	}

	public PasswordEncoder getEncoder() {
		return this.myEncoder;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		String login = auth.getName();
		String senha = auth.getCredentials().toString();

		// Defina suas regras para realizar a autenticação
		User usuarioBd = usuarioService.findByUsername(login);

		if (myEncoder == null) {
			myEncoder = WebMVCSecurity.passwordEncoder();
		}

		Collection<? extends GrantedAuthority> authorities;
		if (usuarioBd != null) {

			if (!myEncoder.matches(senha, usuarioBd.getPassword())) {
				throw new UsernameNotFoundException("Login e/ou Senha inválidos.");
			}

			if (!usuarioBd.isEnabled()) {
				throw new BadCredentialsException("Este usuário está desativado;");
			}
			if (!usuarioBd.isAccountNonExpired()) {
				throw new BadCredentialsException("A conta do usuário está desativada");
			}
			if (!usuarioBd.isAccountNonLocked()) {
				throw new BadCredentialsException("Este usuário está desativado.");
			}
			if (!usuarioBd.isCredentialsNonExpired()) {
				throw new BadCredentialsException("Credenciais expiradas");
			}
			authorities = usuarioBd.getAuthorities();

			usuarioBd.setTokenSessao(Funcoes.cryptPasswordSha256(Calendar.getInstance().toString()));
			usuarioService.salvar2(usuarioBd);
			return new UsernamePasswordAuthenticationToken(usuarioBd, senha, authorities);

		}

		throw new UsernameNotFoundException("Login e/ou Senha inválidos.");
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

}