package br.gov.pb.der.controleveiculosder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pb.der.controleveiculosder.model.TipoVeiculo;

public interface TipoVeiculoRepository extends JpaRepository<TipoVeiculo, Integer> {

	public List<TipoVeiculo> findByOrderByNomeAsc();

	public TipoVeiculo findByNomeAndIdNotIn(String nome, Integer id);

	public List<TipoVeiculo> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

	public Boolean existsByNome(String nome);

	public Boolean existsByNomeAndIdNot(String nome, Integer id);

}
