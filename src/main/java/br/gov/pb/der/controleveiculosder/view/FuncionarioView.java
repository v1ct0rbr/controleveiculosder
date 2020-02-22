package br.gov.pb.der.controleveiculosder.view;

public class FuncionarioView {

	private Long id;
	private String nome;
	private String setorNome;
	private String secretariaNome;

	public FuncionarioView() {
		super();
	}

	public FuncionarioView(Long id, String nome, String setorNome, String secretariaNome) {
		super();
		this.id = id;
		this.nome = nome;
		this.setorNome = setorNome;
		this.secretariaNome = secretariaNome;
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

	public String getSetorNome() {
		return setorNome;
	}

	public void setSetorNome(String setorNome) {
		this.setorNome = setorNome;
	}

	public String getSecretariaNome() {
		return secretariaNome;
	}

	public void setSecretariaNome(String secretariaNome) {
		this.secretariaNome = secretariaNome;
	}

}
