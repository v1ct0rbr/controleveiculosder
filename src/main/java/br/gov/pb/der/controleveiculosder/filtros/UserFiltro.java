package br.gov.pb.der.controleveiculosder.filtros;

import javax.validation.constraints.Size;

public class UserFiltro extends AbstractFilter {

	@Size(min = 0, max = 100)
	private String name;
	@Size(min = 0, max = 100)
	private String email;
	private String username;
	private Integer setorId;
	private int tipo;

	public UserFiltro() {
		super();
		this.sortBy = 1;
		this.sortByMethod = SORTBYMETHOD_ASC;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int role) {
		this.tipo = role;
	}

	public Integer getSetorId() {
		return setorId;
	}

	public void setSetorId(Integer setorId) {
		this.setorId = setorId;
	}

}
