package br.gov.pb.der.controleveiculosder.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.gov.pb.der.controleveiculosder.filtros.FuncionarioFiltro;
import br.gov.pb.der.controleveiculosder.model.Funcionario;
import br.gov.pb.der.controleveiculosder.repository.FuncionarioRepository;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.FuncionarioView;
import br.gov.pb.der.controleveiculosder.view.FuncionarioView2;

@Service
public class FuncionarioService {

	@Autowired
	private FuncionarioRepository repository;

	public Optional<Funcionario> findById(Long id) {
		return repository.findById(id);
	}

	public FuncionarioView2 findViewById(Long id) {
		return repository.findViewById(id);
	}

	public void salvar(Funcionario funcionario) throws DataIntegrityViolationException {
		if (!Funcoes.isValidIntegerParameter(funcionario.getSetor().getId())) {
			throw new DataIntegrityViolationException("Setor é obrigatório e deve ser fornecido corretamente.");
		}

		repository.save(funcionario);
	}

	public Page<Funcionario> filtrar(FuncionarioFiltro filtro, Pageable page) {
		return repository.findPorFiltro(filtro, page);
	}

	public void excluir(Long codigo) {
		// TODO Auto-generated method stub
		repository.deleteById(codigo);
	}

	public boolean existsByNome(String nome) {
		return repository.existsByNomeStartingWithAllIgnoreCase(nome);
	}

	public boolean existsByNomeAndIdNot(String nome, Long id) {
		return repository.existsByNomeStartingWithAllIgnoreCaseAndIdNot(nome, id);
	}

	public boolean funcionarioJaPodeExister(Funcionario funcionario) {
		return Funcoes.isValidLongParameter(funcionario.getId())
				? this.existsByNomeAndIdNot(funcionario.getNome(), funcionario.getId())
				: this.existsByNome(funcionario.getNome());
	}

	public List<FuncionarioView> listByNome(String nome, Long id) {
		return Funcoes.isValidLongParameter(id) ? repository.findByNomeStartingWithAllIgnoreCaseAndIdNot(nome, id)
				: repository.findByNomeStartingWithAllIgnoreCase(nome);
	}

}
