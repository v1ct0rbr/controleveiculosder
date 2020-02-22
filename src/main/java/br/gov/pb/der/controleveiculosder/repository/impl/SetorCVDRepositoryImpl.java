package br.gov.pb.der.controleveiculosder.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.gov.pb.der.controleveiculosder.filtros.AbstractFilter;
import br.gov.pb.der.controleveiculosder.filtros.SetorFiltro;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.model.Secretaria_;
import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.model.SetorCVD_;
import br.gov.pb.der.controleveiculosder.repository.custom.SetorCVDRepositoryCustom;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.SetorView;

public class SetorCVDRepositoryImpl extends GenericRepositoryImpl implements SetorCVDRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long countPorFiltro(SetorFiltro fs) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<SetorCVD> setor = criteriaQuery.from(SetorCVD.class);
		Join<SetorCVD, Secretaria> secretariaJoin = setor.join(SetorCVD_.secretaria, JoinType.LEFT);

		List<Predicate> predicates = testaParametros(builder, fs, setor, secretariaJoin);

		criteriaQuery.multiselect(builder.count(setor));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		Query query = whereClause(fs, criteriaQuery);
		Long resultList = (Long) query.getSingleResult();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<SetorCVD> findPorFiltro(SetorFiltro fs, Pageable page) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SetorCVD> criteriaQuery = builder.createQuery(SetorCVD.class);
		Root<SetorCVD> setor = criteriaQuery.from(SetorCVD.class);
		Join<SetorCVD, Secretaria> secretariaJoin = setor.join(SetorCVD_.secretaria, JoinType.LEFT);

		List<Predicate> predicates = testaParametros(builder, fs, setor, secretariaJoin);

		criteriaQuery.select(setor);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		List<Order> order = new ArrayList<>();

		switch (fs.getSortBy()) {

		case SetorFiltro.SORTBY_ID:
			order.add(defineOrder(fs.getSortByMethod(), builder, setor.get(SetorCVD_.id)));
			break;
		case SetorFiltro.SORTBY_NOME:
			order.add(defineOrder(fs.getSortByMethod(), builder, setor.get(SetorCVD_.nome)));
			break;
		case SetorFiltro.SORTBY_SECRETARIA:
			order.add(defineOrder(fs.getSortByMethod(), builder, secretariaJoin.get(Secretaria_.nome)));
			order.add(defineOrder(AbstractFilter.SORTBYMETHOD_ASC, builder, setor.get(SetorCVD_.nome)));
			break;
		default:
			order.add(defineOrder(AbstractFilter.SORTBYMETHOD_ASC, builder, secretariaJoin.get(Secretaria_.nome)));
			order.add(defineOrder(AbstractFilter.SORTBYMETHOD_ASC, builder, setor.get(SetorCVD_.nome)));
			break;
		}

		criteriaQuery.orderBy(order);
		Query query = whereClause(fs, criteriaQuery);

		Page<SetorCVD> resultado;
		List<SetorCVD> resultList;
		if (fs.isPaginar()) {
			query.setFirstResult((int) page.getOffset());
			query.setMaxResults((int) page.getPageSize());
			resultList = query.getResultList();
			resultado = new PageImpl<SetorCVD>(resultList, page, countPorFiltro(fs));
		} else {
			resultList = query.getResultList();
			resultado = new PageImpl<SetorCVD>(resultList);
		}
		return resultado;

	}

	@Override
	public List<SetorView> listarTodos() {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.controleveiculosder.view.SetorView(s.id, s.nome) FROM SetorCVD AS s order by s.nome asc";
		List<SetorView> result = new ArrayList<>();
		try {
			TypedQuery<SetorView> query = entityManager.createQuery(queryStr, SetorView.class);
			result = query.getResultList();
		} catch (NoResultException e) {
			result = new ArrayList<>();
		}
		return result;
	}

	@Override
	public Query whereClause(SetorFiltro fs, CriteriaQuery<?> criteriaQuery) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(criteriaQuery);

		if (Funcoes.isValidStringValue(fs.getNome())) {
			query.setParameter(SetorFiltro.PARAM_NOME, Funcoes.toLowerCaseForLike(fs.getNome()));
		}

		if (Funcoes.isValidIntegerParameter(fs.getCodigo())) {
			query.setParameter(SetorFiltro.PARAM_ID, fs.getCodigo());
		}
		if (Funcoes.isValidIntegerParameter(fs.getSecretariaId())) {
			query.setParameter(SetorFiltro.PARAM_SECRETARIA_ID, fs.getSecretariaId());
		}

		return query;
	}

	public List<Predicate> testaParametros(CriteriaBuilder builder, SetorFiltro fs, Root<SetorCVD> setor,
			Join<SetorCVD, Secretaria> secretariaJoin) {

		List<Predicate> predicates = new ArrayList<>();
		if (Funcoes.isValidIntegerParameter(fs.getCodigo())) {
			predicates.add(
					builder.equal(setor.get(SetorCVD_.id), builder.parameter(Integer.class, SetorFiltro.PARAM_ID)));
		}

		if (Funcoes.isValidStringValue(fs.getNome())) {
			predicates.add(builder.like(builder.lower(setor.get(SetorCVD_.nome)),
					builder.parameter(String.class, SetorFiltro.PARAM_NOME)));
		}
		if (Funcoes.isValidIntegerParameter(fs.getSecretariaId())) {
			predicates.add(builder.equal(secretariaJoin.get(Secretaria_.id),
					builder.parameter(Integer.class, SetorFiltro.PARAM_SECRETARIA_ID)));
		}

		return predicates;
	}

	@Override
	public List<SetorView> findBySecretariaIdOrderByNomeAsc(Integer id) {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.controleveiculosder.view.SetorView(s.id, s.nome) "
				+ "FROM SetorCVD AS s where s.secretaria.id = :idSecretaria order by s.nome asc";
		List<SetorView> result = new ArrayList<>();
		try {
			TypedQuery<SetorView> query = entityManager.createQuery(queryStr, SetorView.class);
			query.setParameter("idSecretaria", id);
			result = query.getResultList();
		} catch (NoResultException e) {
			result = new ArrayList<>();
		}
		return result;
	}

}
