package br.gov.pb.der.controleveiculosder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Entity
@Table(name = "TipoVeiculo", schema = Parametros.SCH_CONTROLEVEICULOSDER)
public class TipoVeiculo {

	@GenericGenerator(name = "TipoVeiculoSequence", strategy = Parametros.DB_SEQUENCE_STRATEGY, parameters = {
			@Parameter(name = "sequence_name", value = "SEQ_TIPO_VEICULO"),
			@Parameter(name = "initial_value", value = "1"), @Parameter(name = "increment_size", value = "1"),
			@Parameter(name = "schema", value = Parametros.SCH_CONTROLEVEICULOSDER) })
	@Id
	@GeneratedValue(generator = "TipoVeiculoSequence")
	@Column(name = "ID")
	private Integer id;

	@Column(nullable = false, unique = true, length = 100)
	private String nome;

	public TipoVeiculo() {
		super();
	}

	public TipoVeiculo(String nome) {
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
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		TipoVeiculo other = (TipoVeiculo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

}
