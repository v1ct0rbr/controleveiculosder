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

import br.gov.pb.der.controleveiculosder.filtros.FuncionarioFiltro;
import br.gov.pb.der.controleveiculosder.model.Funcionario;
import br.gov.pb.der.controleveiculosder.model.Funcionario_;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.model.Secretaria_;
import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.model.SetorCVD_;
import br.gov.pb.der.controleveiculosder.repository.custom.FuncionarioRepositoryCustom;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.FuncionarioView;
import br.gov.pb.der.controleveiculosder.view.FuncionarioView2;

public class FuncionarioRepositoryImpl extends GenericRepositoryImpl implements FuncionarioRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long countPorFiltro(FuncionarioFiltro fs) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Funcionario> root = criteriaQuery.from(Funcionario.class);
		Join<Funcionario, SetorCVD> setor = root.join(Funcionario_.setor, JoinType.LEFT);
		Join<SetorCVD, Secretaria> secretaria = setor.join(SetorCVD_.secretaria, JoinType.LEFT);

		List<Predicate> predicates = testaParametros(builder, fs, root, setor, secretaria);

		criteriaQuery.multiselect(builder.count(root));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		Query query = whereClause(fs, criteriaQuery);
		Long resultList = (Long) query.getSingleResult();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Funcionario> findPorFiltro(FuncionarioFiltro fs, Pageable page) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Funcionario> criteriaQuery = builder.createQuery(Funcionario.class);
		Root<Funcionario> root = criteriaQuery.from(Funcionario.class);
		Join<Funcionario, SetorCVD> setor = root.join(Funcionario_.setor, JoinType.LEFT);
		Join<SetorCVD, Secretaria> secretaria = setor.join(SetorCVD_.secretaria, JoinType.LEFT);

		List<Predicate> predicates = testaParametros(builder, fs, root, setor, secretaria);

		criteriaQuery.select(root);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		List<Order> order = new ArrayList<>();

		switch (fs.getSortBy()) {
		case FuncionarioFiltro.SORTBY_NOME:
			order.add(defineOrder(fs.getSortByMethod(), builder, root.get(Funcionario_.nome)));
			break;
		case FuncionarioFiltro.SORTBY_SETOR:
			order = defineOrder(fs.getSortByMethod(), builder, setor.get(SetorCVD_.nome), root.get(Funcionario_.nome));
			break;
		default:
			order.add(defineOrder(fs.getSortByMethod(), builder, secretaria.get(Secretaria_.nome)));
			order.add(defineOrder(FuncionarioFiltro.SORTBYMETHOD_ASC, builder, setor.get(SetorCVD_.nome)));
			order.add(defineOrder(FuncionarioFiltro.SORTBYMETHOD_ASC, builder, root.get(Funcionario_.nome)));
			break;
		}
		criteriaQuery.orderBy(order);
		Query query = whereClause(fs, criteriaQuery);

		Page<Funcionario> resultado;
		List<Funcionario> resultList;
		if (fs.isPaginar()) {
			query.setFirstResult((int) page.getOffset());
			query.setMaxResults((int) page.getPageSize());
			resultList = query.getResultList();
			resultado = new PageImpl<Funcionario>(resultList, page, countPorFiltro(fs));
		} else {
			resultList = query.getResultList();
			resultado = new PageImpl<Funcionario>(resultList);
		}
		return resultado;

	}

	@Override
	public Query whereClause(FuncionarioFiltro fs, CriteriaQuery<?> criteriaQuery) {
		// TODO Auto-generated method stub
		Query query = entityManager.createQuery(criteriaQuery);

		if (Funcoes.isValidStringValue(fs.getNome())) {
			query.setParameter(FuncionarioFiltro.PARAM_NOME, Funcoes.toLowerCaseForLike(fs.getNome()));
		}

		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			query.setParameter(FuncionarioFiltro.PARAM_SETOR_ID, fs.getSetorId());
		}
		if (Funcoes.isValidIntegerParameter(fs.getSecretariaId())) {
			query.setParameter(FuncionarioFiltro.PARAM_SECRETARIA_ID, fs.getSecretariaId());
		}

		return query;
	}

	public List<Predicate> testaParametros(CriteriaBuilder builder, FuncionarioFiltro fs, Root<Funcionario> funcionario,
			Join<Funcionario, SetorCVD> setor, Join<SetorCVD, Secretaria> secretariaJoin) {

		List<Predicate> predicates = new ArrayList<>();

		if (Funcoes.isValidStringValue(fs.getNome())) {
			predicates.add(builder.like(builder.lower(funcionario.get(Funcionario_.nome)),
					builder.parameter(String.class, FuncionarioFiltro.PARAM_NOME)));
		}
		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			predicates.add(builder.equal(setor.get(SetorCVD_.id),
					builder.parameter(Integer.class, FuncionarioFiltro.PARAM_SETOR_ID)));
		}

		if (Funcoes.isValidIntegerParameter(fs.getSecretariaId())) {
			predicates.add(builder.equal(secretariaJoin.get(Secretaria_.id),
					builder.parameter(Integer.class, FuncionarioFiltro.PARAM_SECRETARIA_ID)));
		}

		return predicates;
	}

	@Override
	public List<FuncionarioView> findByNomeStartingWithAllIgnoreCase(String nome) {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.controleveiculosder.view.FuncionarioView(f.id, f.nome, se.nome, sec.nome) "
				+ "FROM Funcionario AS f left join f.setor as se " + "left join se.secretaria as sec "
				+ "where lower(f.nome) like :nomefunc order by f.setor.secretaria.nome asc, f.setor.nome asc";
		List<FuncionarioView> result = new ArrayList<>();
		try {
			TypedQuery<FuncionarioView> query = entityManager.createQuery(queryStr, FuncionarioView.class);
			query.setParameter("nomefunc", Funcoes.toLowerCaseForStartingWith(nome));
			result = query.getResultList();
		} catch (NoResultException e) {
			result = new ArrayList<>();
		}
		return result;
	}

	@Override
	public List<FuncionarioView> findByNomeStartingWithAllIgnoreCaseAndIdNot(String nome, Long id) {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.controleveiculosder.view.FuncionarioView(f.id, f.nome, se.nome, sec.nome) "
				+ "FROM Funcionario AS f left join f.setor as se " + "left join se.secretaria as sec "
				+ "where lower(f.nome) like :nomefunc and f.id <> :idfuncionario order by f.setor.secretaria.nome asc, f.setor.nome asc";
		List<FuncionarioView> result = new ArrayList<>();
		try {
			TypedQuery<FuncionarioView> query = entityManager.createQuery(queryStr, FuncionarioView.class);
			query.setParameter("nomefunc", Funcoes.toLowerCaseForStartingWith(nome));
			query.setParameter("idfuncionario", id);
			result = query.getResultList();
		} catch (NoResultException e) {
			result = new ArrayList<>();
		}
		return result;
	}

	@Override
	public FuncionarioView2 findViewById(Long id) {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.controleveiculosder.view.FuncionarioView2(f.id, f.nome, f.email, f.celular, sec.id, se.id) "
				+ "FROM Funcionario AS f left join f.setor as se " + "left join se.secretaria as sec "
				+ "where f.id = :idFuncionario";
		FuncionarioView2 result = null;
		try {
			TypedQuery<FuncionarioView2> query = entityManager.createQuery(queryStr, FuncionarioView2.class);
			query.setParameter("idFuncionario", id);
			result = query.getSingleResult();
		} catch (NoResultException e) {
			result = null;
		}
		return result;
	}

}
