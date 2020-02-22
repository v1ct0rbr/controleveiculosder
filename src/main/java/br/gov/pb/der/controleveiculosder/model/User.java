package br.gov.pb.der.controleveiculosder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.utils.MyDateUtils;
import br.gov.pb.der.controleveiculosder.utils.Parametros;

@Entity
@Table(name = "User", schema = Parametros.SCH_SECURITY)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int TIP_USUARIO = 1;
	public static final int TIP_CLIENTE = 2;

	@GenericGenerator(name = "UserSequence", strategy = Parametros.DB_SEQUENCE_STRATEGY, parameters = {
			@Parameter(name = "sequence_name", value = "SEQ_USER"), @Parameter(name = "initial_value", value = "1"),
			@Parameter(name = "increment_size", value = "1"),
			@Parameter(name = "schema", value = Parametros.SCH_SECURITY) })
	@Id
	@GeneratedValue(generator = "UserSequence")
	@Column(name = "ID")
	private Long id;

	@Column(name = "username", unique = true, nullable = true, length = 100)
	@Size(min = 0, max = 100)
	private String username;

	@Size(min = 1, max = 100)
	@NotNull
	@Column(name = "name", length = 100, nullable = false, unique = false)
	private String name;

	@Column(name = "password", length = 100)
	private String password;

	@Transient
	private String password2;

	@Size(min = 0, max = 100)
	@Column(name = "email", unique = true, length = 100, nullable = true)
	private String email;

	@Size(min = 0, max = 20)
	@Column(name = "fone", length = 20, nullable = true)
	private String fone;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MMM-YYYY")
	private Calendar dataCadastro;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", schema = Parametros.SCH_SECURITY, joinColumns = @JoinColumn(name = "userid", unique = false), inverseJoinColumns = @JoinColumn(name = "roleid", unique = false))
	private List<Role> roles;

	@Transient
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	private Calendar loginTime;

	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;

	@Column(name = "token_sessao")
	private String tokenSessao;

	@Column(name = "tipo", nullable = false)
	private Integer tipo;

	@ManyToOne
	@JoinColumn(name = "setor_id")
	private Setor setor;

	public User() {
		this.roles = new ArrayList<>();
	}

	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.email = user.email;
		this.password = user.password;
		this.dataCadastro = user.dataCadastro;
		this.name = user.name;
		this.roles = user.roles;

		this.enabled = user.enabled;
		this.accountNonExpired = user.accountNonExpired;
		this.accountNonLocked = user.accountNonLocked;
		this.credentialsNonExpired = user.credentialsNonExpired;
		this.tipo = user.tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long userid) {
		this.id = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		if (Funcoes.isValidStringValue(email))
			this.email = email;
		else
			this.email = null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (Funcoes.isValidStringValue(username))
			this.username = username;
		else
			this.username = null;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Calendar getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Calendar dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getFormattedDate() {
		return MyDateUtils.formatCalendarTimezone(this.dataCadastro, MyDateUtils.PATTERN_MAIN);
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<String> regras = new ArrayList<String>();
		for (Role r : getRoles()) {
			regras.add(r.getAuthority());
		}

		String roles = StringUtils.collectionToCommaDelimitedString(regras);
		return AuthorityUtils.commaSeparatedStringToAuthorityList(roles);
	}

	@SuppressWarnings("unused")
	private List<GrantedAuthority> getAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role userProfile : getRoles()) {
			System.out.println("UserProfile : " + userProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getAuthority()));
		}
		System.out.print("authorities :" + authorities);
		return authorities;
	}

	public boolean hasAuthority(String authority) {
		for (Role userProfile : getRoles()) {
			if (userProfile.getAuthority().equals(authority)) {
				return true;
			}
		}
		return false;
	}

	public Calendar getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Calendar loginTime) {
		this.loginTime = loginTime;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTokenSessao() {
		return tokenSessao;
	}

	public void setTokenSessao(String tokenSessao) {
		this.tokenSessao = tokenSessao;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = Funcoes.formatarTelefone(fone);
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	@Override
	public String toString() {
		return name + (email != null ? " - " + email : "");
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}