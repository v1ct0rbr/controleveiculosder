package br.gov.pb.der.controleveiculosder.config.parameters;

public class EmpresaConfig {

	// parametros.put("empresa_nome", Parametros.EMPRESA_NOME);
	// parametros.put("empresa_setor_nome", Parametros.EMPRESA_SETOR);
	// parametros.put("empresa_localidade", Parametros.EMPRESA_LOCALIDADE);
	// parametros.put("empresa_endereco", Parametros.EMPRESA_ENDERECO);
	// parametros.put("empresa_telefone", Parametros.EMPRESA_TELEFONE);

	private String empresaNome;
	private String empresaSetor;
	private String empresaLocalidade;
	private String empresaEndereco;
	private String empresaTelefone;
	private String empresaEmail;

	public String getEmpresaNome() {
		return empresaNome;
	}

	public void setEmpresaNome(String empresaNome) {
		this.empresaNome = empresaNome;
	}

	public String getEmpresaSetor() {
		return empresaSetor;
	}

	public void setEmpresaSetor(String empresaSetor) {
		this.empresaSetor = empresaSetor;
	}

	public String getEmpresaLocalidade() {
		return empresaLocalidade;
	}

	public void setEmpresaLocalidade(String empresaLocalidade) {
		this.empresaLocalidade = empresaLocalidade;
	}

	public String getEmpresaEndereco() {
		return empresaEndereco;
	}

	public void setEmpresaEndereco(String empresaEndereco) {
		this.empresaEndereco = empresaEndereco;
	}

	public String getEmpresaTelefone() {
		return empresaTelefone;
	}

	public void setEmpresaTelefone(String empresaTelefone) {
		this.empresaTelefone = empresaTelefone;
	}

	public String getEmpresaEmail() {
		return empresaEmail;
	}

	public void setEmpresaEmail(String empresaEmail) {
		this.empresaEmail = empresaEmail;
	}

}
