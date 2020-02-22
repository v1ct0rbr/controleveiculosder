package br.gov.pb.der.controleveiculosder.model;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Entity
@Table(name = "Veiculo", schema = Parametros.SCH_CONTROLEVEICULOSDER)
public class Veiculo {

	@GenericGenerator(name = "VeiculoSequence", strategy = Parametros.DB_SEQUENCE_STRATEGY, parameters = {
			@Parameter(name = "sequence_name", value = "SEQ_VEICULO"), @Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "1"),
			@Parameter(name = "schema", value = Parametros.SCH_CONTROLEVEICULOSDER) })
	@Id
	@GeneratedValue(generator = "VeiculoSequence")
	@Column(name = "ID")
	private Long id;

	@Column(nullable = false, name = "placa", length = 10, unique = true)
	@NotNull
	private String placa;

	@Transient
	private String placaTemp;

	@Column(name = "adesivo", unique = true, length = 10)
	private String adesivo;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "funcionario_id", nullable = false)
	@Valid
	private Funcionario funcionario;

	@Column(name = "modeloveiculo", length = 100, nullable = true)
	private String modeloVeiculo;

	@OneToOne
	@JoinColumn(name = "tipoveiculo_id")
	private TipoVeiculo tipo;

	@Temporal(TemporalType.DATE)
	private Calendar dataCadastro;

	public Veiculo() {
		super();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Veiculo(String placa) {
		super();
		this.placa = placa;
	}

	public String getAdesivo() {
		return adesivo;
	}

	public void setAdesivo(String adesivo) {
		if (Funcoes.isValidStringValue(adesivo))
			this.adesivo = adesivo;
		else
			this.adesivo = null;
	}

	public String getPlaca() {
		if (Funcoes.isValidStringValue(this.placa) && this.placa.length() > 3) {
			return this.placa.substring(0, 3) + "-" + this.placa.substring(3);
		}
		return placa;
	}

	public String getPlacaTemp() {
		return placaTemp;
	}

	public void setPlacaTemp(String placaTemp) {
		this.placaTemp = placaTemp;
	}

	public void setPlaca(String placa) {
		if (Funcoes.isValidStringValue(placa))
			this.placa = Funcoes.formatarPlaca(placa);
		else
			this.placa = null;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public String getModeloVeiculo() {
		return modeloVeiculo;
	}

	public void setModeloVeiculo(String modeloVeiculo) {
		this.modeloVeiculo = modeloVeiculo;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public TipoVeiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVeiculo tipo) {
		this.tipo = tipo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adesivo == null) ? 0 : adesivo.hashCode());
		result = prime * result + ((placa == null) ? 0 : placa.hashCode());
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
		Veiculo other = (Veiculo) obj;
		if (adesivo == null) {
			if (other.adesivo != null)
				return false;
		} else if (!adesivo.equals(other.adesivo))
			return false;
		if (placa == null) {
			if (other.placa != null)
				return false;
		} else if (!placa.equals(other.placa))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return modeloVeiculo + " / placa=" + placa + " / Adesivo: " + adesivo;
	}

}
