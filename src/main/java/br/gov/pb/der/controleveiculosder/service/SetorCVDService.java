package br.gov.pb.der.controleveiculosder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.pb.der.controleveiculosder.filtros.SetorFiltro;
import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.repository.SetorCVDRepository;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.SetorView;

@Service
public class SetorCVDService {

	@Autowired
	private SetorCVDRepository repository;

	@Autowired
	private SecretariaService secService;

	public Optional<SetorCVD> findById(Integer id) {
		return repository.findById(id);
	}

	public SetorCVD findByNomeAndIdNotIn(String nome, Integer id) {
		return repository.findByNomeAndIdNotIn(nome, id);
	}

	public void salvar(SetorCVD setor) throws DataIntegrityViolationException {

		repository.save(setor);
	}

	public Page<SetorCVD> filtrar(SetorFiltro filtro, Pageable page) {
		return repository.findPorFiltro(filtro, page);
	}

	public List<SetorCVD> filtrarCombo(String nomeSetor) {
		if (!Funcoes.isValidStringValue(nomeSetor)) {
			nomeSetor = "";
		}
//		return repository.findByDescricaoContainingIgnoreCaseOrderByDescricaoAsc(nomeSetor);
		return repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nomeSetor);
	}

	public List<SetorCVD> listar() {
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

	public List<SetorView> listarTodosPorSecretaria(Integer id) {
		return repository.findBySecretariaIdOrderByNomeAsc(id);
	}

	public SetorCVD convertViewToSetor(SetorView view) throws RuntimeException {
		SetorCVD setor = new SetorCVD();
		Secretaria secretaria = null;
		StringBuilder sb = new StringBuilder();

		if (!Funcoes.isValidStringValue(view.getNome())) {
			sb.append(" - O nome do setor deve ser informado");
		}
		if (Funcoes.isValidIntegerParameter(view.getSecretariaId())) {
			secretaria = secService.findById(view.getSecretariaId()).get();
			if (secretaria == null) {
				sb.append(" - Secretaria não encontrada;");
			}
		} else {
			sb.append(" - Secretaria deve ser fornecida;");
		}
		if (sb.length() > 0) {
			throw new RuntimeException(sb.toString());
		}
		setor.setSecretaria(secretaria);
		setor.setNome(view.getNome());
		setor.setRamal(view.getTelefone());
		setor.setNome(view.getNome());
		return setor;
	}

	public boolean setorExists(SetorCVD setor) {
		// TODO Auto-generated method stub
		return !Funcoes.isValidIntegerParameter(setor.getId())
				? repository.existsByNomeAndSecretariaId(setor.getNome(), setor.getSecretaria().getId())
				: repository.existsByNomeAndSecretariaIdAndIdNot(setor.getNome(), setor.getSecretaria().getId(),
						setor.getId());
	}

}
