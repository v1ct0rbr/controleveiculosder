package br.gov.pb.der.controleveiculosder.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Secretaria.class)
public abstract class Secretaria_ {

	public static volatile SingularAttribute<Secretaria, String> ramalPrincipal;
	public static volatile SingularAttribute<Secretaria, String> prefixoTel;
	public static volatile SingularAttribute<Secretaria, String> nome;
	public static volatile SingularAttribute<Secretaria, Integer> id;
	public static volatile ListAttribute<Secretaria, SetorCVD> setores;

	public static final String RAMAL_PRINCIPAL = "ramalPrincipal";
	public static final String PREFIXO_TEL = "prefixoTel";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String SETORES = "setores";

}

