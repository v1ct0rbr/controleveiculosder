package br.gov.pb.der.controleveiculosder.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import br.gov.pb.der.controleveiculosder.filtros.AbstractFilter;

public abstract class GenericRepositoryImpl {

	public Order defineOrder(Integer tipo, CriteriaBuilder builder, Path<?> path) {
		return tipo == AbstractFilter.SORTBYMETHOD_DESC ? builder.desc(path) : builder.asc(path);

	}

	public List<Order> defineOrder(Integer tipo, CriteriaBuilder builder, Path<?> path, Path<?> secondPathAsc) {
		List<Order> orderList = new ArrayList<>();
		orderList.add(tipo == AbstractFilter.SORTBYMETHOD_DESC ? builder.desc(path) : builder.asc(path));
		orderList.add(builder.asc(secondPathAsc));

		return orderList;

	}

}
