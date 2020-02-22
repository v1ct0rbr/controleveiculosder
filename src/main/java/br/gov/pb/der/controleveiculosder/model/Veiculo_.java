package br.gov.pb.der.controleveiculosder.model;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Veiculo.class)
public abstract class Veiculo_ {

	public static volatile SingularAttribute<Veiculo, String> modeloVeiculo;
	public static volatile SingularAttribute<Veiculo, TipoVeiculo> tipo;
	public static volatile SingularAttribute<Veiculo, String> adesivo;
	public static volatile SingularAttribute<Veiculo, Long> id;
	public static volatile SingularAttribute<Veiculo, Funcionario> funcionario;
	public static volatile SingularAttribute<Veiculo, Calendar> dataCadastro;
	public static volatile SingularAttribute<Veiculo, String> placa;

	public static final String MODELO_VEICULO = "modeloVeiculo";
	public static final String TIPO = "tipo";
	public static final String ADESIVO = "adesivo";
	public static final String ID = "id";
	public static final String FUNCIONARIO = "funcionario";
	public static final String DATA_CADASTRO = "dataCadastro";
	public static final String PLACA = "placa";

}

