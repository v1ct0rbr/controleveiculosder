package br.gov.pb.der.controleveiculosder.filtros;

import javax.validation.constraints.Max;

public class SetorFiltro extends AbstractFilter {

	public static final String PARAM_ID = "codigo";
	public static final String PARAM_NOME = "nome";
	public static final String PARAM_SECRETARIA_ID = "secretariaId";

	public static final int SORTBY_NOME = 1;
	public static final int SORTBY_ID = 2;
	public static final int SORTBY_SECRETARIA = 3;

	@Max(value = 65625, message = "código não pode ser superior a 65625")
	private Integer codigo;
	private String nome;
	private Integer secretariaId;

	public SetorFiltro() {
		super();
		this.sortBy = SORTBY_NOME;
		this.sortByMethod = SORTBYMETHOD_ASC;
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

	public Integer getSecretariaId() {
		return secretariaId;
	}

	public void setSecretariaId(Integer secretariaId) {
		this.secretariaId = secretariaId;
	}

}
