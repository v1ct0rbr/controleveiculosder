package br.gov.pb.der.controleveiculosder.view;

import javax.validation.constraints.NotNull;

public class AlterarPerfil {

	@NotNull(message = "A senha atual deve ser fornecida")
	private String senhaAtual;
	@NotNull(message = "A nova senha deve ser fornecida")
	private String password1;
	@NotNull(message = "Confirme a nova senha")
	private String password2;

	public AlterarPerfil() {
		super();
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

}
