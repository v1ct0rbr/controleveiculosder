package br.gov.pb.der.controleveiculosder.repository.custom;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GenericRepositoryCustom<TIPOID, TIPOOBJ, TIPOFILTRO> {

//	public List<Predicate> generatePredicateByFilter(T filtro);

	public Page<TIPOOBJ> findPorFiltro(TIPOFILTRO filtro, Pageable page);

	public TIPOID countPorFiltro(TIPOFILTRO fs);

	public Query whereClause(TIPOFILTRO fs, CriteriaQuery<?> criteriaQuery);

}
