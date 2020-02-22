package br.gov.pb.der.controleveiculosder.filtros;

import javax.validation.constraints.Size;

public class VeiculoFiltro extends AbstractFilter {

	public static final int SORTBY_ADESIVO = 1;
	public static final int SORTBY_PLACA = 2;
	public static final int SORTBY_FUNCIONARIO = 3;
	public static final int SORTBY_SECRETARIA = 4;
	public static final int SORTBY_SETOR = 5;
	public static final int SORTBY_MODELO = 6;

	public static final String PARAM_ADESIVO = "adesivo";
	public static final String PARAM_PLACA = "placa";
	public static final String PARAM_FUNCIONARIO = "funcionarioNome";
	public static final String PARAM_SECRETARIA = "secretariaId";
	public static final String PARAM_SETOR = "setorId";
	public static final String PARAM_TIPOVEICULO = "tipoveiculoId";
	public static final String PARAM_MODELO = "modeloNome";

	@Size(min = 0, max = 10)
	private String adesivo;
	@Size(min = 0, max = 10)
	private String placa;
	@Size(min = 0, max = 100)
	private String funcionarioNome;
	private Integer secretariaId;
	private Integer setorId;
	private Integer tipoveiculoId;
	private String modelo;

	public VeiculoFiltro() {
		super();
		this.sortBy = 1;
		this.sortByMethod = SORTBYMETHOD_ASC;
	}

	public String getAdesivo() {
		return adesivo;
	}

	public void setAdesivo(String adesivo) {
		this.adesivo = adesivo;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getFuncionarioNome() {
		return funcionarioNome;
	}

	public void setFuncionarioNome(String funcionarioNome) {
		this.funcionarioNome = funcionarioNome;
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

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Integer getTipoveiculoId() {
		return tipoveiculoId;
	}

	public void setTipoveiculoId(Integer tipoveiculoId) {
		this.tipoveiculoId = tipoveiculoId;
	}

}
