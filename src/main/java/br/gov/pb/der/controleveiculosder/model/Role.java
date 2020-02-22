package br.gov.pb.der.controleveiculosder.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Entity
@Table(name = "role", schema = Parametros.SCH_SECURITY)
public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int ROLE_ID_DIT_USER = 1;
	public static final int ROLE_ID_ADMIN = 2;
	public static final int ROLE_ID_OPERADOR = 3;
	public static final int ROLE_ID_SUPERUSER = 4;

	public static final int ROLE_ID_CLIENTE = 10;

	public static final String ROLE_SUPERUSER = "SUPERUSER";
	public static final String ROLE_DIT_USER = "DEROS_USER";

	public Role(Integer id) {
		super();
		this.id = id;
	}

	public Role(Integer id, String authority) {
		super();
		this.id = id;
		this.authority = authority;
	}

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "authority", unique = true, length = 30)
	private String authority;

	public Role() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	// public int getRoleId() {
	// return roleId;
	// }
	//
	// public void setRoleId(int roleId) {
	// this.roleId = roleId;
	// }

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return authority;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authority == null) ? 0 : authority.hashCode());
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
		Role other = (Role) obj;
		if (authority == null) {
			if (other.authority != null)
				return false;
		} else if (!authority.equals(other.authority))
			return false;
		return true;
	}

}
