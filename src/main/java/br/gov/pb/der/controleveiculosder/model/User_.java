package br.gov.pb.der.controleveiculosder.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SingularAttribute<User, Setor> setor;
	public static volatile SingularAttribute<User, Integer> tipo;
	public static volatile SingularAttribute<User, String> tokenSessao;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, Boolean> credentialsNonExpired;
	public static volatile SingularAttribute<User, Boolean> enabled;
	public static volatile SingularAttribute<User, String> fone;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, Boolean> accountNonExpired;
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, Calendar> dataCadastro;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, Boolean> accountNonLocked;

	public static final String SETOR = "setor";
	public static final String TIPO = "tipo";
	public static final String TOKEN_SESSAO = "tokenSessao";
	public static final String ROLES = "roles";
	public static final String CREDENTIALS_NON_EXPIRED = "credentialsNonExpired";
	public static final String ENABLED = "enabled";
	public static final String FONE = "fone";
	public static final String PASSWORD = "password";
	public static final String NAME = "name";
	public static final String ACCOUNT_NON_EXPIRED = "accountNonExpired";
	public static final String ID = "id";
	public static final String DATA_CADASTRO = "dataCadastro";
	public static final String EMAIL = "email";
	public static final String USERNAME = "username";
	public static final String ACCOUNT_NON_LOCKED = "accountNonLocked";

}

