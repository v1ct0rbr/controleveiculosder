package br.gov.pb.der.controleveiculosder.view;

public class SecretariaView {

	private Integer id;

	private String nome;

	public SecretariaView() {

	}

	public SecretariaView(String nome) {
		super();
		this.nome = nome;
	}

	public SecretariaView(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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

	public void setNome(String nome) {
		this.nome = nome;
	}

}
