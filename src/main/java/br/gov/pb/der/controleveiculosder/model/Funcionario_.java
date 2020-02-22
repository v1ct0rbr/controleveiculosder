package br.gov.pb.der.controleveiculosder.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Funcionario.class)
public abstract class Funcionario_ {

	public static volatile SingularAttribute<Funcionario, SetorCVD> setor;
	public static volatile ListAttribute<Funcionario, Veiculo> veiculos;
	public static volatile SingularAttribute<Funcionario, String> celular;
	public static volatile SingularAttribute<Funcionario, String> nome;
	public static volatile SingularAttribute<Funcionario, Long> id;
	public static volatile SingularAttribute<Funcionario, String> email;

	public static final String SETOR = "setor";
	public static final String VEICULOS = "veiculos";
	public static final String CELULAR = "celular";
	public static final String NOME = "nome";
	public static final String ID = "id";
	public static final String EMAIL = "email";

}

