package br.gov.pb.der.controleveiculosder.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.gov.pb.der.controleveiculosder.filtros.UserFiltro;
import br.gov.pb.der.controleveiculosder.model.Setor;
import br.gov.pb.der.controleveiculosder.model.Setor_;
import br.gov.pb.der.controleveiculosder.model.User;
import br.gov.pb.der.controleveiculosder.model.User_;
import br.gov.pb.der.controleveiculosder.repository.custom.UserRepositoryCustom;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;

public class UserRepositoryImpl implements UserRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Long countPorFiltro(UserFiltro fs) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		Root<User> user = criteriaQuery.from(User.class);
		Join<User, Setor> setor = user.join(User_.setor, JoinType.LEFT);

		List<Predicate> predicates = new ArrayList<>();
		if (Funcoes.isValidStringValue(fs.getName())) {
			ParameterExpression<String> paramValor = builder.parameter(String.class, "name");
			predicates.add(builder.like(builder.lower(user.<String>get("name")), paramValor));
		}
		if (Funcoes.isValidStringValue(fs.getEmail())) {
			ParameterExpression<String> paramValor = builder.parameter(String.class, "email");
			predicates.add(builder.like(builder.lower(user.<String>get("email")), paramValor));
		}
		if (Funcoes.isValidStringValue(fs.getUsername())) {
			ParameterExpression<String> paramValor = builder.parameter(String.class, "username");
			predicates.add(builder.like(builder.lower(user.<String>get("username")), paramValor));
		}
		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			predicates.add(builder.equal(setor.<Integer>get(Setor_.id), builder.parameter(Integer.class, "setorId")));
		}

		if (fs.getTipo() != 0 && fs.getTipo() > 0) {
			ParameterExpression<Integer> paramValor = builder.parameter(Integer.class, "tipo");
			predicates.add(builder.equal(user.<Integer>get("tipo"), paramValor));
		}

		criteriaQuery.multiselect(builder.count(user.get("id")));
		criteriaQuery.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);

		if (Funcoes.isValidStringValue(fs.getName())) {
			query.setParameter("name", Funcoes.toLowerCaseForLike(fs.getName()));
		}
		if (Funcoes.isValidStringValue(fs.getEmail())) {
			query.setParameter("email", Funcoes.toLowerCaseForLike(fs.getEmail()));
		}
		if (Funcoes.isValidStringValue(fs.getUsername())) {
			query.setParameter("username", Funcoes.toLowerCaseForLike(fs.getUsername()));
		}
		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			query.setParameter("setorId", fs.getSetorId());
		}

		if (fs.getTipo() != 0 && fs.getTipo() > 0) {
			query.setParameter("tipo", fs.getTipo());
		}

		Long resultList = query.getSingleResult();
		return resultList;
	}

	@Override
	public Page<User> findByFiltro(UserFiltro fs, Pageable page) {
		// TODO Auto-generated method stub
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
		Root<User> user = criteriaQuery.from(User.class);
		Join<User, Setor> setor = user.join(User_.setor, JoinType.LEFT);

		Page<User> resultado;

		List<Predicate> predicates = new ArrayList<>();
		if (Funcoes.isValidStringValue(fs.getName())) {
			ParameterExpression<String> paramValor = builder.parameter(String.class, "name");
			predicates.add(builder.like(builder.lower(user.<String>get("name")), paramValor));
		}
		if (Funcoes.isValidStringValue(fs.getEmail())) {
			ParameterExpression<String> paramValor = builder.parameter(String.class, "email");
			predicates.add(builder.like(builder.lower(user.<String>get("email")), paramValor));
		}
		if (Funcoes.isValidStringValue(fs.getUsername())) {
			ParameterExpression<String> paramValor = builder.parameter(String.class, "username");
			predicates.add(builder.like(builder.lower(user.<String>get("username")), paramValor));
		}

		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			predicates.add(builder.equal(setor.<Integer>get(Setor_.id), builder.parameter(Integer.class, "setorId")));
		}
		if (fs.getTipo() != 0 && fs.getTipo() > 0) {
			ParameterExpression<Integer> paramValor = builder.parameter(Integer.class, "tipo");
			predicates.add(builder.equal(user.<Integer>get("tipo"), paramValor));
		}
		criteriaQuery.select(user);
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<User> query = entityManager.createQuery(criteriaQuery);
		if (Funcoes.isValidStringValue(fs.getName())) {
			query.setParameter("name", Funcoes.toLowerCaseForLike(fs.getName()));
		}
		if (Funcoes.isValidStringValue(fs.getEmail())) {
			query.setParameter("email", Funcoes.toLowerCaseForLike(fs.getEmail()));
		}
		if (Funcoes.isValidStringValue(fs.getUsername())) {
			query.setParameter("username", Funcoes.toLowerCaseForLike(fs.getUsername()));
		}
		if (Funcoes.isValidIntegerParameter(fs.getSetorId())) {
			query.setParameter("setorId", fs.getSetorId());
		}
		if (fs.getTipo() != 0 && fs.getTipo() > 0) {
			query.setParameter("tipo", fs.getTipo());
		}

		query.setFirstResult((int) page.getOffset());
		query.setMaxResults((int) page.getPageSize());
		List<User> resultList = query.getResultList();

		resultado = new PageImpl<User>(resultList, page, countPorFiltro(fs));

		return resultado;

	}

	@Override
	public User findByUsernameOrEmail(String username, String email) {
		// TODO Auto-generated method stub
		TypedQuery<User> query = entityManager
				.createQuery("select u from User u where (u.username = :username or u.email = :email)", User.class);
		query.setParameter("username", username);
		query.setParameter("email", email);
		query.setMaxResults(1);
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			System.err.println("não encontrado...ok");
		}
		return null;
	}

	@Override
	public User findByUsernameOrEmailAndIdNot(String username, String email, Long id) {
		// TODO Auto-generated method stub
		TypedQuery<User> query = entityManager.createQuery(
				"select u from User u where (u.username = :username or u.email = :email) and u.id <> :id", User.class);
		query.setParameter("username", username);
		query.setParameter("email", email);
		query.setParameter("id", id);
		query.setMaxResults(1);

		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			System.err.println("não encontrado...ok");
		}
		return null;
	}

}
