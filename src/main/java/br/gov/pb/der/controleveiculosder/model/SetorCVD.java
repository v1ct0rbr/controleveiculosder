package br.gov.pb.der.controleveiculosder.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

/**
 *
 * @author victorqueiroga
 */
@Entity
@Table(name = "SetorCVD", schema = Parametros.SCH_CONTROLEVEICULOSDER)
public class SetorCVD implements Serializable {

	private static final long serialVersionUID = 1L;
	// public static final String COL_PREFIX = "SET_";

	@GenericGenerator(name = "SetorCVDSequence", strategy = Parametros.DB_SEQUENCE_STRATEGY, parameters = {
			@Parameter(name = "sequence_name", value = "SEQ_SETOR_CVD"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1"),
			@Parameter(name = "schema", value = Parametros.SCH_CONTROLEVEICULOSDER) })
	@Id
	@GeneratedValue(generator = "SetorCVDSequence")
	@Column(name = "ID")
	private Integer id;
	@Column(name = "NOME", nullable = false, length = 100)
	@Size(min = 1, max = 100)
	private String nome;
	@OneToMany(mappedBy = "setor", fetch = FetchType.LAZY)
	private List<Funcionario> funcionarios;
	@Size(min = 0, max = 4)
	@Column(name = "telefone", nullable = true, length = 4)
	private String ramal;

	@ManyToOne
	@JoinColumn(name = "secretaria_id", nullable = false)
	private Secretaria secretaria;

	public SetorCVD() {
		super();
	}

	public SetorCVD(Integer id) {
		super();
		this.id = id;
	}

	public SetorCVD(String nome) {
		super();
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
//		return nome.replace(',', ' ');
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

//	

	public SetorCVD(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String telefone) {
		this.ramal = telefone;
	}

	public String getTelefone() {
		StringBuilder tel = new StringBuilder();
		boolean hasSecretaria = this.secretaria != null;
		boolean hasPrefixSecretaria = hasSecretaria && Funcoes.isValidStringValue(this.secretaria.getPrefixoTel());
		boolean hasSufixSecretaria = hasSecretaria && Funcoes.isValidStringValue(this.secretaria.getRamalPrincipal());

		if (hasPrefixSecretaria) {
			tel.append(secretaria.getPrefixoTel() + "-");
		}

		if (Funcoes.isValidStringValue(this.ramal)) {
			tel.append(this.ramal);
		} else if (hasSufixSecretaria)
			tel.append(this.secretaria.getRamalPrincipal());
		else {
			tel.append("XXXX");
		}
		return tel.toString();

	}

	public Secretaria getSecretaria() {
		return secretaria;
	}

	public void setSecretaria(Secretaria secretaria) {
		this.secretaria = secretaria;
	}

	public boolean isSecretariaValida() {
		return this.secretaria != null && Funcoes.isValidIntegerParameter(this.secretaria.getId());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetorCVD other = (SetorCVD) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome + " (" + getTelefone() + ")";
	}

	public void formataAtributos() {
		// TODO Auto-generated method stub
		this.nome = this.nome.replace(',', ' ');
		this.ramal = this.ramal != null && this.ramal.length() > 0 ? this.ramal.replace(',', ' ') : "";
	}

}
