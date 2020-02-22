package br.gov.pb.der.controleveiculosder.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import br.gov.pb.der.controleveiculosder.filtros.VeiculoFiltro;
import br.gov.pb.der.controleveiculosder.model.Funcionario;
import br.gov.pb.der.controleveiculosder.model.Veiculo;
import br.gov.pb.der.controleveiculosder.repository.VeiculoRepository;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;

@Service
public class VeiculoService {

	@Autowired
	private VeiculoRepository repository;
//
	@Autowired
	private FuncionarioService funcionarioService;

	public Optional<Veiculo> findById(Long id) {
		return repository.findById(id);
	}

	public void salvar(Veiculo veiculo) throws DataIntegrityViolationException, CloneNotSupportedException {

		Funcionario fun = null;
		if (!Funcoes.isValidIntegerParameter(veiculo.getTipo().getId())) {
			veiculo.setTipo(null);
		}

		if (Funcoes.isValidLongParameter(veiculo.getFuncionario().getId())) {
			Optional<Funcionario> funcTemp = funcionarioService.findById(veiculo.getFuncionario().getId());
			fun = funcTemp.isPresent() ? funcTemp.get() : new Funcionario();
			fun.clone(veiculo.getFuncionario());
			funcionarioService.salvar(fun);
			veiculo.setFuncionario(fun);
		}
		repository.save(veiculo);

	}

	public Page<Veiculo> filtrar(VeiculoFiltro filtro, Pageable page) {
		return repository.findPorFiltro(filtro, page);
	}

	public void excluir(Long codigo) {
		// TODO Auto-generated method stub
		repository.deleteById(codigo);
	}

	public Boolean veiculoJaExistePorAdesivo(Veiculo veiculo) {
		return Funcoes.isValidLongParameter(veiculo.getId())
				? repository.existsByAdesivoAndIdNot(veiculo.getAdesivo(), veiculo.getId())
				: repository.existsByAdesivo(veiculo.getAdesivo());
	}

	public Boolean veiculoJaExistePorPlaca(Veiculo veiculo) {
		return Funcoes.isValidLongParameter(veiculo.getId())
				? repository.existsByPlacaAndIdNot(veiculo.getPlaca(), veiculo.getId())
				: repository.existsByPlaca(veiculo.getPlaca());
	}

	public Boolean veiculoJaExisteByPlacaNotOrAdesivo(Veiculo veiculo) {
		return Funcoes.isValidLongParameter(veiculo.getId())
				? repository.existsByPlacaOrAdesivoAndIdNot(veiculo.getPlaca(), veiculo.getAdesivo(), veiculo.getId())
				: repository.existsByPlacaOrAdesivo(veiculo.getPlaca(), veiculo.getAdesivo());
	}

	public boolean isValid(Veiculo veiculo, Errors erros) {
		if (veiculoJaExistePorPlaca(veiculo)) {
			erros.rejectValue("placa", "sem_codigo", "Placa já existe");
		}
		if (veiculoJaExistePorAdesivo(veiculo)) {
			erros.rejectValue("adesivo", "sem_codigo", "Adesivo já existe");
		}

		return !erros.hasErrors();

	}

}
