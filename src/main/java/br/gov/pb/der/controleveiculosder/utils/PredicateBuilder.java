package br.gov.pb.der.controleveiculosder.utils;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

public class PredicateBuilder {

	public static final int TE_STRING_EQUAL = 1;
	public static final int TE_STRING_LIKE = 2;
	public static final int TE_STRING_LIKE_IC = 3;
	public static final int TE_NUMBER_EQ = 4;
	public static final int TE_NUMBER_GT = 5;
	public static final int TE_NUMBER_LT = 6;

	private int method;
	private CriteriaBuilder builder;
	private Path<?> path;
	private String atributo;

	private List<Predicate> predicates;

	public PredicateBuilder() {
		super();
	}

	public PredicateBuilder(int method, CriteriaBuilder builder, Path<?> path, String atributo) {
		super();
		this.method = method;
		this.builder = builder;
		this.path = path;
		this.atributo = atributo;
		this.predicates = new ArrayList<Predicate>();

	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

	public CriteriaBuilder getBuilder() {
		return builder;
	}

	public void setBuilder(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public Path<?> getPath() {
		return path;
	}

	public void setPath(Path<?> path) {
		this.path = path;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}
	////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public PredicateBuilder addPredicate(int method, CriteriaBuilder builder, Path<?> path, String atributo) {
		switch (method) {
		case TE_STRING_EQUAL:
			predicates.add(builder.equal(path, builder.parameter(String.class, atributo)));
			break;
		case TE_STRING_LIKE:
			predicates.add(builder.like((Expression<String>) path, builder.parameter(String.class, atributo)));
			break;
		case TE_STRING_LIKE_IC:
			predicates.add(
					builder.like(builder.lower((Expression<String>) path), builder.parameter(String.class, atributo)));
			break;
		case TE_NUMBER_EQ:
			predicates.add(builder.equal(path, builder.parameter(Integer.class, atributo)));
			break;
		case TE_NUMBER_GT:
			predicates.add(builder.gt((Expression<? extends Number>) path, builder.parameter(Number.class, atributo)));
			break;
		case TE_NUMBER_LT:
			predicates.add(builder.lt((Expression<? extends Number>) path, builder.parameter(Number.class, atributo)));
			break;
		}

		return this;
	}

}
