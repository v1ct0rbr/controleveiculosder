package br.gov.pb.der.controleveiculosder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pb.der.controleveiculosder.model.Funcionario;
import br.gov.pb.der.controleveiculosder.repository.custom.FuncionarioRepositoryCustom;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>, FuncionarioRepositoryCustom {

	public Boolean existsByNomeStartingWithAllIgnoreCase(String nome);

	public Boolean existsByNomeStartingWithAllIgnoreCaseAndIdNot(String nome, Long id);

}
