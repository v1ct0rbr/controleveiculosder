package br.gov.pb.der.controleveiculosder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.pb.der.controleveiculosder.filtros.SetorFiltro;
import br.gov.pb.der.controleveiculosder.model.Setor;
import br.gov.pb.der.controleveiculosder.repository.SetorRepository;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.SetorView;

@Service
public class SetorService {

	@Autowired
	private SetorRepository repository;

	public Setor findById(Integer id) {
		return repository.findById(id).get();
	}

	public Setor findByNomeAndIdNotIn(String nome, Integer id) {
		return repository.findByNomeAndIdNotIn(nome, id);
	}

	public void salvar(Setor setor) throws DataIntegrityViolationException {
		if (setor.getResponsavel() != null) {
			if (!Funcoes.isValidLongParameter(setor.getResponsavel().getId())) {
				setor.setResponsavel(null);
			}
		}
		repository.save(setor);
	}

	public Page<Setor> filtrar(SetorFiltro filtro, Pageable page) {
		return repository.findPorFiltro(filtro, page);
	}

	public List<Setor> filtrarCombo(String nomeSetor) {
		if (!Funcoes.isValidStringValue(nomeSetor)) {
			nomeSetor = "";
		}
//		return repository.findByDescricaoContainingIgnoreCaseOrderByDescricaoAsc(nomeSetor);
		return repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nomeSetor);
	}

	public List<Setor> listar() {
		// TODO Auto-generated method stub
		return repository.findAllByOrderByNomeAsc();
	}

	public void excluir(Integer codigo) {
		// TODO Auto-generated method stub
		repository.deleteById(codigo);
	}

	public List<SetorView> listarTodos() {
		// TODO Auto-generated method stub
		return repository.listarTodos();
	}

	public boolean existsById(Integer id) {
		return repository.existsById(id);
	}

}
