package br.gov.pb.der.controleveiculosder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.repository.SecretariaRepository;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.SecretariaView;

@Service
public class SecretariaService {

	@Autowired
	private SecretariaRepository repository;

	public Optional<Secretaria> findById(Integer id) {
		return repository.findById(id);
	}

	public Secretaria findByNomeAndIdNotIn(String nome, Integer id) {
		return repository.findByNomeAndIdNotIn(nome, id);
	}

	public void salvar(Secretaria entity) throws DataIntegrityViolationException {

		repository.save(entity);
	}

	public List<Secretaria> listar() {
		// TODO Auto-generated method stub
		return repository.findByOrderByNomeAsc();
	}

	public List<SecretariaView> listarView() {
		return repository.listarTodos();
	}

	public void excluir(Integer codigo) {
		// TODO Auto-generated method stub
		repository.deleteById(codigo);
	}

	public boolean existsById(Integer id) {
		return repository.existsById(id);
	}

	public boolean existsByNome(Secretaria secretaria) {
		return Funcoes.isValidIntegerParameter(secretaria.getId())
				? repository.existsByNomeAndIdNot(secretaria.getNome(), secretaria.getId())
				: repository.existsByNome(secretaria.getNome());
	}

}
