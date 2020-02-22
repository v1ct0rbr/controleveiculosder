package br.gov.pb.der.controleveiculosder.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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

import br.gov.pb.der.controleveiculosder.filtros.VeiculoFiltro;
import br.gov.pb.der.controleveiculosder.model.Funcionario;
import br.gov.pb.der.controleveiculosder.model.Funcionario_;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.model.Secretaria_;
import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.model.SetorCVD_;
import br.gov.pb.der.controleveiculosder.model.TipoVeiculo;
import br.gov.pb.der.controleveiculosder.model.TipoVeiculo_;
import br.gov.pb.der.controleveiculosder.model.Veiculo;
import br.gov.pb.der.controleveiculosder.model.Veiculo_;
import br.gov.pb.der.controleveiculosder.repository.custom.VeiculoRepositoryCustom;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;

public class VeiculoRepositoryImpl extends GenericRepositoryImpl implements VeiculoRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long countPorFiltro(VeiculoFiltro fs) {

		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<Veiculo> veiculoRoot = criteriaQuery.from(Veiculo.class);
		Join<Veiculo, TipoVeiculo> tipoJoin = veiculoRoot.join(Veiculo_.tipo, JoinType.LEFT);
		Join<Veiculo, Funcionario> funcionarioJoin = veiculoRoot.join(Veiculo_.funcionario, JoinType.LEFT);
		Join<Funcionario, SetorCVD> setorJoin = funcionarioJoin.join(Funcionario_.setor, JoinType.LEFT);
		Join<SetorCVD, Secretaria> secretariaJoin = setorJoin.join(SetorCVD_.secretaria, JoinType.LEFT);

		List<Predicate> predicates = testaParametros(builder, fs, veiculoRoot, funcionarioJoin, setorJoin,
				secretariaJoin, tipoJoin);

		criteriaQuery.multiselect(builder.count(veiculoRoot.get(Veiculo_.placa)));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		Query query = whereClause(fs, criteriaQuery);

		Long resultList = (Long) query.getSingleResult();
		return resultList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<Veiculo> findPorFiltro(VeiculoFiltro fs, Pageable page) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Veiculo> criteriaQuery = builder.createQuery(Veiculo.class);
		Root<Veiculo> veiculoRoot = criteriaQuery.from(Veiculo.class);
		Join<Veiculo, TipoVeiculo> tipoJoin = veiculoRoot.join(Veiculo_.tipo, JoinType.LEFT);
		Join<Veiculo, Funcionario> funcionarioJoin = veiculoRoot.join(Veiculo_.funcionario, JoinType.LEFT);
		Join<Funcionario, SetorCVD> setorJoin = funcionarioJoin.join(Funcionario_.setor, JoinType.LEFT);
		Join<SetorCVD, Secretaria> secretariaJoin = setorJoin.join(SetorCVD_.secretaria, JoinType.LEFT);

		Page<Veiculo> resultado;

		List<Predicate> predicates = testaParametros(builder, fs, veiculoRoot, funcionarioJoin, setorJoin,
				secretariaJoin, tipoJoin);

		criteriaQuery.select(veiculoRoot);

		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		Order order;

		switch (fs.getSortBy()) {
		case VeiculoFiltro.SORTBY_ADESIVO:
			order = defineOrder(fs.getSortByMethod(), builder, veiculoRoot.<String>get(Veiculo_.adesivo));
			break;
		case VeiculoFiltro.SORTBY_PLACA:
			order = defineOrder(fs.getSortByMethod(), builder, veiculoRoot.<String>get(Veiculo_.placa));
			break;
		case VeiculoFiltro.SORTBY_FUNCIONARIO:
			order = defineOrder(fs.getSortByMethod(), builder, funcionarioJoin.get(Funcionario_.nome));
			break;
		case VeiculoFiltro.SORTBY_SECRETARIA:
			order = defineOrder(fs.getSortByMethod(), builder, secretariaJoin.get(Secretaria_.nome));
			break;
		case VeiculoFiltro.SORTBY_SETOR:
			order = defineOrder(fs.getSortByMethod(), builder, setorJoin.get(SetorCVD_.nome));
			break;
		case VeiculoFiltro.SORTBY_MODELO:
			order = defineOrder(fs.getSortByMethod(), builder, veiculoRoot.get(Veiculo_.modeloVeiculo));
			break;
		default:
			order = defineOrder(VeiculoFiltro.SORTBYMETHOD_ASC, builder, veiculoRoot.<String>get(Veiculo_.placa));
			break;
		}

		criteriaQuery.orderBy(order);

		Query query = whereClause(fs, criteriaQuery);

		List<Veiculo> resultList;
		if (fs.isPaginar()) {
			query.setFirstResult((int) page.getOffset());
			query.setMaxResults((int) page.getPageSize());
			resultList = query.getResultList();
			resultado = new PageImpl<Veiculo>(resultList, page, countPorFiltro(fs));
		} else {
			query.setMaxResults(1000);
			resultList = query.getResultList();
			resultado = new PageImpl<Veiculo>(resultList);
		}
		return resultado;
	}

	@Override
	public Query whereClause(VeiculoFiltro fs, CriteriaQuery<?> criteriaQuery) {

		Query query = entityManager.createQuery(criteriaQuery);

		if (Funcoes.isValidStringValue(fs.getAdesivo())) {
			query.setParameter(VeiculoFiltro.PARAM_ADESIVO, fs.getAdesivo());
		}
		if (Funcoes.isValidStringValue(fs.getPlaca())) {
			query.setParameter(VeiculoFiltro.PARAM_PLACA,
					Funcoes.toLowerCaseForLike(Funcoes.formatarPlaca(fs.getPlaca())));
		}
		if (Funcoes.isValidStringValue(fs.getFuncionarioNome())) {
			query.setParameter(VeiculoFiltro.PARAM_FUNCIONARIO, Funcoes.toLowerCaseForLike(fs.getFuncionarioNome()));
		}

		if (Funcoes.isValidStringValue(fs.getModelo())) {
			query.setParameter(VeiculoFiltro.PARAM_MODELO, Funcoes.toLowerCaseForLike(fs.getModelo()));
		}
		if (Funcoes.isValidIntegerParameter(fs.getSecretariaId())) {
			query.setParameter(VeiculoFiltro.PARAM_SECRETARIA, fs.getSecretariaId());
		}

		if (Funcoes.isValidIntegerParameter(fs.getTipoveiculoId())) {
			query.setParameter(VeiculoFiltro.PARAM_TIPOVEICULO, fs.getTipoveiculoId());
		}
		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			query.setParameter(VeiculoFiltro.PARAM_SETOR, fs.getSetorId());
		}

		return query;
	}

	public List<Predicate> testaParametros(CriteriaBuilder builder, VeiculoFiltro fs, Root<Veiculo> veiculoRoot,
			Join<Veiculo, Funcionario> funcionarioJoin, Join<Funcionario, SetorCVD> setorJoin,
			Join<SetorCVD, Secretaria> secretariaJoin, Join<Veiculo, TipoVeiculo> tipoJoin) {

		List<Predicate> predicates = new ArrayList<>();
		if (Funcoes.isValidStringValue(fs.getAdesivo())) {
			predicates.add(builder.equal(veiculoRoot.<String>get(Veiculo_.adesivo),
					builder.parameter(String.class, VeiculoFiltro.PARAM_ADESIVO)));
		}
		if (Funcoes.isValidStringValue(fs.getPlaca())) {
			predicates.add(builder.like(builder.lower(veiculoRoot.<String>get(Veiculo_.placa)),
					builder.parameter(String.class, VeiculoFiltro.PARAM_PLACA)));
		}
		if (Funcoes.isValidStringValue(fs.getFuncionarioNome())) {
			predicates.add(builder.like(builder.lower(funcionarioJoin.<String>get(Funcionario_.nome)),
					builder.parameter(String.class, VeiculoFiltro.PARAM_FUNCIONARIO)));
		}
		if (Funcoes.isValidStringValue(fs.getModelo())) {
			predicates.add(builder.like(builder.lower(veiculoRoot.<String>get(Veiculo_.modeloVeiculo)),
					builder.parameter(String.class, VeiculoFiltro.PARAM_MODELO)));
		}

		if (Funcoes.isValidIntegerParameter(fs.getSecretariaId())) {
			predicates.add(builder.equal(secretariaJoin.<Integer>get(Secretaria_.id),
					builder.parameter(Integer.class, VeiculoFiltro.PARAM_SECRETARIA)));
		}

		if (Funcoes.isValidIntegerParameter(fs.getTipoveiculoId())) {
			predicates.add(builder.equal(tipoJoin.<Integer>get(TipoVeiculo_.id),
					builder.parameter(Integer.class, VeiculoFiltro.PARAM_TIPOVEICULO)));
		}
		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			predicates.add(builder.equal(setorJoin.<Integer>get(SetorCVD_.id),
					builder.parameter(Integer.class, VeiculoFiltro.PARAM_SETOR)));
		}
		return predicates;
	}

}
