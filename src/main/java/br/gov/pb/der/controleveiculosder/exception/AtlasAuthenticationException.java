package br.gov.pb.der.controleveiculosder.exception;

import org.springframework.security.core.AuthenticationException;

public class AtlasAuthenticationException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtlasAuthenticationException(String message) {
		super(message);
	}

	public AtlasAuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}

}