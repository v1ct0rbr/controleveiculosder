package br.gov.pb.der.controleveiculosder.filtros;

public class ConfiguracaoFiltro extends AbstractFilter {

	private String nome;
	private String parametro;

	public ConfiguracaoFiltro() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

}
