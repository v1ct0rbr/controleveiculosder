package br.gov.pb.der.controleveiculosder.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Entity
@Table(name = "Secretaria", schema = Parametros.SCH_CONTROLEVEICULOSDER)
public class Secretaria implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Integer id;

	@Column(nullable = false, length = 100, unique = true)
	private String nome;

	@Size(max = 4)
	@Column(name = "prefixo_tel", length = 4)
	private String prefixoTel;
	@Size(max = 4)
	@Column(name = "ramal_principal", length = 4)
	private String ramalPrincipal;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, mappedBy = "secretaria", fetch = FetchType.LAZY)
	private List<SetorCVD> setores;

	public Secretaria() {
		super();
	}

	public Secretaria(String nome) {
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

	public List<SetorCVD> getSetores() {
		return setores;
	}

	public void setSetores(List<SetorCVD> setores) {
		this.setores = setores;
	}

	public String getPrefixoTel() {
		return prefixoTel;
	}

	public void setPrefixoTel(String prefixoTel) {
		this.prefixoTel = prefixoTel;
	}

	public String getRamalPrincipal() {
		return ramalPrincipal;
	}

	public void setRamalPrincipal(String ramalPrincipal) {
		this.ramalPrincipal = ramalPrincipal;
	}

	public String getTelefone() {
		StringBuilder tel = new StringBuilder();
		if (Funcoes.isValidStringValue(this.prefixoTel)) {
			tel.append(this.prefixoTel + "-");
		}
		if (Funcoes.isValidStringValue(this.ramalPrincipal)) {
			tel.append(this.ramalPrincipal);
		}

		return tel.toString().trim().length() > 0 ? " (" + tel.toString() + ")" : "";
	}

	public String getInfo() {
		return this.getNome() + getTelefone();
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
		Secretaria other = (Secretaria) obj;
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
