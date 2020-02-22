package br.gov.pb.der.controleveiculosder.filtros;

public class SecretariaFiltro extends AbstractFilter {

	private Integer codigo;
	private String nome;

	public SecretariaFiltro() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
