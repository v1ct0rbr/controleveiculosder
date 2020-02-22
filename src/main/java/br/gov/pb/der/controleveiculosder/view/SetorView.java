package br.gov.pb.der.controleveiculosder.view;

public class SetorView {

	private Integer id;

	private String nome;

	private String telefone;

	private Integer secretariaId;

	public SetorView() {

	}

	public SetorView(Integer id, String descricao) {
		super();
		this.id = id;
		this.nome = descricao;
	}

	public SetorView(Integer id, String descricao, String telefone, Integer secretariaId) {
		super();
		this.id = id;
		this.nome = descricao;
		this.secretariaId = secretariaId;
		this.telefone = telefone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public void setNome(String descricao) {
		this.nome = descricao;
	}

	public Integer getSecretariaId() {
		return secretariaId;
	}

	public void setSecretariaId(Integer secretariaId) {
		this.secretariaId = secretariaId;
	}

	@Override
	public String toString() {
		return nome;
	}

}
