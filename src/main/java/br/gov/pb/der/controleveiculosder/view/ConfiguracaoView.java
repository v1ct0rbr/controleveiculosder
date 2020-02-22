package br.gov.pb.der.controleveiculosder.view;

public class ConfiguracaoView {

	private Long id;
	private String nome;

	public ConfiguracaoView() {
		super();
	}

	public ConfiguracaoView(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;

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

}
