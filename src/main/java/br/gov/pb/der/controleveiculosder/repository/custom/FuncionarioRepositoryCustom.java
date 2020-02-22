package br.gov.pb.der.controleveiculosder.repository.custom;

import java.util.List;

import br.gov.pb.der.controleveiculosder.filtros.FuncionarioFiltro;
import br.gov.pb.der.controleveiculosder.model.Funcionario;
import br.gov.pb.der.controleveiculosder.view.FuncionarioView;
import br.gov.pb.der.controleveiculosder.view.FuncionarioView2;

public interface FuncionarioRepositoryCustom extends GenericRepositoryCustom<Long, Funcionario, FuncionarioFiltro> {

	public List<FuncionarioView> findByNomeStartingWithAllIgnoreCase(String nome);

	public List<FuncionarioView> findByNomeStartingWithAllIgnoreCaseAndIdNot(String nome, Long id);

	public FuncionarioView2 findViewById(Long id);
	
}
