package br.gov.pb.der.controleveiculosder.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Setor.class)
public abstract class Setor_ {

	public static volatile SingularAttribute<Setor, String> telefone;
	public static volatile SingularAttribute<Setor, Boolean> interno;
	public static volatile SingularAttribute<Setor, String> nome;
	public static volatile SingularAttribute<Setor, Integer> id;
	public static volatile ListAttribute<Setor, User> funcionarios;
	public static volatile SingularAttribute<Setor, User> responsavel;

	public static final String TELEFONE = "telefone";
	public static final String INTERNO = "interno";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String FUNCIONARIOS = "funcionarios";
	public static final String RESPONSAVEL = "responsavel";

}

