package br.gov.pb.der.controleveiculosder.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SetorCVD.class)
public abstract class SetorCVD_ {

	public static volatile SingularAttribute<SetorCVD, String> nome;
	public static volatile SingularAttribute<SetorCVD, Integer> id;
	public static volatile ListAttribute<SetorCVD, Funcionario> funcionarios;
	public static volatile SingularAttribute<SetorCVD, Secretaria> secretaria;
	public static volatile SingularAttribute<SetorCVD, String> ramal;

	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String FUNCIONARIOS = "funcionarios";
	public static final String SECRETARIA = "secretaria";
	public static final String RAMAL = "ramal";

}

