package br.gov.pb.der.controleveiculosder.security;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.gov.pb.der.controleveiculosder.model.Role;
import br.gov.pb.der.controleveiculosder.model.User;

public class CustomUserDetails extends User implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String salt;

	public CustomUserDetails(User user, String salt) {
		super(user);
		this.salt = salt;

	}

	public Calendar getTimeLogin() {
		return super.getLoginTime();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}

	public boolean hasRole(String role) {
		for (Role r : super.getRoles()) {
			if (r.getAuthority().equals(role))
				return true;
		}

		return false;
	}

	public boolean hasAuthority(String role) {
		for (Role r : super.getRoles()) {
			if (r.getAuthority().equals(role))
				return true;
		}
		return false;
	}

	public String getTokenSessao() {
		return super.getTokenSessao();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
