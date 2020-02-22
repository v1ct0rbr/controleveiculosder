package br.gov.pb.der.controleveiculosder.view;

public class FuncionarioView2 {

	private Long id;
	private String nome;
	private String email;
	private String celular;
	private Integer secretariaId;
	private Integer setorId;;

	public FuncionarioView2() {
		super();
	}

	public FuncionarioView2(Long id, String nome, String email, String celular, Integer secretariaId, Integer setorId) {
		super();
		this.id = id;
		this.nome = nome;
		this.celular = celular;
		this.secretariaId = secretariaId;
		this.setorId = setorId;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Integer getSecretariaId() {
		return secretariaId;
	}

	public void setSecretariaId(Integer secretariaId) {
		this.secretariaId = secretariaId;
	}

	public Integer getSetorId() {
		return setorId;
	}

	public void setSetorId(Integer setorId) {
		this.setorId = setorId;
	}

}
