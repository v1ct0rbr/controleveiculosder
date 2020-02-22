package br.gov.pb.der.controleveiculosder.filtros;

public class FuncionarioFiltro extends AbstractFilter {

	public static final String PARAM_NOME = "nome";
	public static final String PARAM_SETOR_ID = "setorId";
	public static final String PARAM_SECRETARIA_ID = "secretariaId";

	public static final int SORTBY_NOME = 1;
	public static final int SORTBY_SETOR = 2;
	public static final int SORTBY_SECRETARIA = 3;

	private String nome;
	private Integer setorId;
	private Integer secretariaId;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getSetorId() {
		return setorId;
	}

	public void setSetorId(Integer setorId) {
		this.setorId = setorId;
	}

	public Integer getSecretariaId() {
		return secretariaId;
	}

	public void setSecretariaId(Integer secretariaId) {
		this.secretariaId = secretariaId;
	}

}
