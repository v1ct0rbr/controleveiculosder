package br.gov.pb.der.controleveiculosder.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author victorqueiroga
 */
@Entity
@Table(name = "Setor")
public class Setor implements Serializable {

	private static final long serialVersionUID = 1L;
	// public static final String COL_PREFIX = "SET_";

	@Id
	@Min(value = 1)
	@NotNull
	private Integer id;

	@Column(name = "NOME", nullable = false, length = 100)
	@Size(min = 1, max = 100)
	private String nome;
	@OneToOne
	@JoinColumn(name = "responsavel_id")
	private User responsavel;
	@OneToMany(mappedBy = "setor", fetch = FetchType.LAZY)
	private List<User> funcionarios;
	@Column(name = "telefone", nullable = true, length = 15)
	private String telefone;
	@Column(name = "interno", nullable = true)
	private boolean interno;

	public Setor() {
		super();
	}

	public Setor(Integer id) {
		super();
		this.id = id;
	}

	public Setor(String nome) {
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

//	

	public Setor(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public User getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(User responsavel) {
		this.responsavel = responsavel;
	}

	public List<User> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<User> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isInterno() {
		return interno;
	}

	public void setInterno(boolean interno) {
		this.interno = interno;
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
		Setor other = (Setor) obj;
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
