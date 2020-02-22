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
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.gov.pb.der.controleveiculosder.filtros.AbstractFilter;
import br.gov.pb.der.controleveiculosder.filtros.SetorFiltro;
import br.gov.pb.der.controleveiculosder.model.Setor;
import br.gov.pb.der.controleveiculosder.model.Setor_;
import br.gov.pb.der.controleveiculosder.repository.custom.SetorRepositoryCustom;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.SetorView;

public class SetorRepositoryImpl implements SetorRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Setor> findPorFiltro(SetorFiltro fs, Pageable page) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Setor> criteriaQuery = builder.createQuery(Setor.class);
		Root<Setor> setor = criteriaQuery.from(Setor.class);

		List<Predicate> predicates = new ArrayList<>();

		if (Funcoes.isValidIntegerParameter(fs.getCodigo())) {

			predicates.add(builder.equal(setor.get(Setor_.id), builder.parameter(Integer.class, "codigo")));
		}

		if (Funcoes.isValidStringValue(fs.getNome())) {
			predicates
					.add(builder.like(builder.lower(setor.get(Setor_.nome)), builder.parameter(String.class, "nome")));
		}

		criteriaQuery.select(setor);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		Order order;
		switch (fs.getSortByMethod()) {
		case AbstractFilter.SORTBYMETHOD_DESC:
			switch (fs.getSortBy()) {
			case SetorFiltro.SORTBY_ID:
				order = builder.desc(setor.get(Setor_.id));
				break;
			case SetorFiltro.SORTBY_NOME:
				order = builder.desc(setor.get(Setor_.nome));
				break;

			default:
				order = builder.desc(setor.get(Setor_.nome));
				break;
			}
			break;
		default:
			switch (fs.getSortBy()) {

			case SetorFiltro.SORTBY_ID:
				order = builder.asc(setor.get(Setor_.id));
				break;
			case SetorFiltro.SORTBY_NOME:
				order = builder.asc(setor.get(Setor_.nome));
				break;

			default:
				order = builder.asc(setor.get(Setor_.nome));
				break;

			}

		}
		criteriaQuery.orderBy(order);
		TypedQuery<Setor> query = entityManager.createQuery(criteriaQuery);

		if (Funcoes.isValidStringValue(fs.getNome())) {
			query.setParameter("nome", Funcoes.toLowerCaseForLike(fs.getNome()));
		}

		if (Funcoes.isValidIntegerParameter(fs.getCodigo())) {
			query.setParameter("codigo", fs.getCodigo());
		}

		Page<Setor> resultado;
		List<Setor> resultList;
		if (fs.isPaginar()) {
			query.setFirstResult((int) page.getOffset());
			query.setMaxResults((int) page.getPageSize());
			resultList = query.getResultList();
			resultado = new PageImpl<Setor>(resultList, page, countPorFiltro(fs));
		} else {
			resultList = query.getResultList();
			resultado = new PageImpl<Setor>(resultList);
		}
		return resultado;

	}

	@Override
	public Integer countPorFiltro(SetorFiltro fs) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);

		Root<Setor> setor = criteriaQuery.from(Setor.class);

		List<Predicate> predicates = new ArrayList<>();

		if (Funcoes.isValidIntegerParameter(fs.getCodigo())) {
			predicates.add(builder.equal(setor.get(Setor_.id), builder.parameter(Integer.class, "codigo")));
		}

		if (Funcoes.isValidStringValue(fs.getNome())) {
			predicates
					.add(builder.like(builder.lower(setor.get(Setor_.nome)), builder.parameter(String.class, "nome")));
		}

		criteriaQuery.multiselect(builder.count(setor.get(Setor_.id)));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);

		if (Funcoes.isValidStringValue(fs.getNome())) {
			query.setParameter("nome", Funcoes.toLowerCaseForLike(fs.getNome()));
		}

		if (Funcoes.isValidIntegerParameter(fs.getCodigo())) {
			query.setParameter("codigo", fs.getCodigo());
		}

		Integer resultList = query.getSingleResult().intValue();
		return resultList;
	}

	@Override
	public List<SetorView> listarTodos() {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.controleveiculosder.view.SetorView(s.id, s.nome) FROM Setor AS s order by s.nome asc";
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
		return null;
	}

}
