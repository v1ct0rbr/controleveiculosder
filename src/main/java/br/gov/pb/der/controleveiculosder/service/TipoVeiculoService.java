package br.gov.pb.der.controleveiculosder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.gov.pb.der.controleveiculosder.model.TipoVeiculo;
import br.gov.pb.der.controleveiculosder.repository.TipoVeiculoRepository;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;

@Service
public class TipoVeiculoService {

	@Autowired
	private TipoVeiculoRepository repository;

	public Optional<TipoVeiculo> findById(Integer id) {
		return repository.findById(id);
	}

	public TipoVeiculo findByNomeAndIdNotIn(String nome, Integer id) {
		return repository.findByNomeAndIdNotIn(nome, id);
	}

	public void salvar(TipoVeiculo setor) throws DataIntegrityViolationException {

		repository.save(setor);
	}

	public List<TipoVeiculo> filtrarCombo(String nomeSetor) {
		if (!Funcoes.isValidStringValue(nomeSetor)) {
			nomeSetor = "";
		}
//		return repository.findByDescricaoContainingIgnoreCaseOrderByDescricaoAsc(nomeSetor);
		return repository.findByNomeContainingIgnoreCaseOrderByNomeAsc(nomeSetor);
	}

	public List<TipoVeiculo> listar() {
		// TODO Auto-generated method stub
		return repository.findByOrderByNomeAsc();
	}

	public void excluir(Integer codigo) {
		// TODO Auto-generated method stub
		repository.deleteById(codigo);
	}

	public boolean existsByNome(TipoVeiculo tipo) {
		// TODO Auto-generated method stub
		return !Funcoes.isValidIntegerParameter(tipo.getId()) ? repository.existsByNome(tipo.getNome())
				: repository.existsByNomeAndIdNot(tipo.getNome(), tipo.getId());
	}

}
