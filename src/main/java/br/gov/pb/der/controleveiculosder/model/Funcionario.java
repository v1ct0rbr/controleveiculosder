package br.gov.pb.der.controleveiculosder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.utils.MyId;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Entity
@Table(name = "Funcionario", schema = Parametros.SCH_CONTROLEVEICULOSDER)
public class Funcionario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GenericGenerator(name = "FuncionarioSequence", strategy = Parametros.DB_SEQUENCE_STRATEGY, parameters = {
			@Parameter(name = "sequence_name", value = "SEQ_FUNCIONARIO"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1"),
			@Parameter(name = "schema", value = Parametros.SCH_CONTROLEVEICULOSDER) })
	@Id
	@GeneratedValue(generator = "FuncionarioSequence")
	@Column(name = "ID")
	private Long id;

	@Column(name = "nome", nullable = false, length = 100)
	@NotNull
	private String nome;

	@OneToOne
	@JoinColumn(name = "setor_id", nullable = false)
	private SetorCVD setor;

	@Column(length = 15)
	private String celular;

	@Column(nullable = true, length = 100)
	private String email;

	@OneToMany(mappedBy = "funcionario", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
	private List<Veiculo> veiculos;

	@Transient
	private List<MyId<Long>> veiculosMarcadosParaExclusao;

	public Funcionario() {
		super();
		this.veiculos = new ArrayList<Veiculo>();
	}

	public Funcionario(String nome) {
		super();
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

	public SetorCVD getSetor() {
		return setor;
	}

	public void setSetor(SetorCVD setor) {
		this.setor = setor;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {

		this.celular = Funcoes.formatarTelefone(celular);
	}

	public String getCelularFormatado() {
		if (Funcoes.isValidStringValue(this.celular) && this.celular.trim().length() > 1) {
			return Funcoes.getTelefoneFormatado(this.celular);
		}
		return "";
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	public List<MyId<Long>> getVeiculosMarcadosParaExclusao() {
		return veiculosMarcadosParaExclusao;
	}

	public void setVeiculosMarcadosParaExclusao(List<MyId<Long>> veiculosMarcadosParaExclusao) {
		this.veiculosMarcadosParaExclusao = veiculosMarcadosParaExclusao;
	}

	public boolean hasSetorValido() {
		return this.setor != null && Funcoes.isValidIntegerParameter(this.setor.getId());
	}

	//////////////////////////////
	public String getContato() {
		return (Funcoes.isValidStringValue(celular) ? celular : "")
				+ (Funcoes.isValidStringValue(this.email) ? " - " + this.email : "");
	}

	public void clone(Funcionario funcionario) {
		this.celular = funcionario.getCelular();
		this.email = funcionario.getEmail();
		this.nome = funcionario.getNome();
		this.setor = funcionario.getSetor();

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
		Funcionario other = (Funcionario) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

}
