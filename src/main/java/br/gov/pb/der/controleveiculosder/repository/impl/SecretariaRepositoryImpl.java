package br.gov.pb.der.controleveiculosder.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.gov.pb.der.controleveiculosder.repository.custom.SecretariaRepositoryCustom;
import br.gov.pb.der.controleveiculosder.view.SecretariaView;

public class SecretariaRepositoryImpl implements SecretariaRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<SecretariaView> listarTodos() {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.controleveiculosder.view.SecretariaView(s.id, s.nome) FROM Secretaria AS s order by s.nome asc";
		List<SecretariaView> result = new ArrayList<>();
		try {
			TypedQuery<SecretariaView> query = entityManager.createQuery(queryStr, SecretariaView.class);
			result = query.getResultList();
		} catch (NoResultException e) {
			result = new ArrayList<>();
		}
		return result;
	}

}
