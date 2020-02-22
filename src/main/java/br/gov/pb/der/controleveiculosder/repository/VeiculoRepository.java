package br.gov.pb.der.controleveiculosder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pb.der.controleveiculosder.model.Veiculo;
import br.gov.pb.der.controleveiculosder.repository.custom.VeiculoRepositoryCustom;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>, VeiculoRepositoryCustom {

	public List<Veiculo> findByPlacaIsContainingIgnoreCaseOrderByPlacaAsc(String nome);

	public List<Veiculo> findByAdesivoIgnoreCaseOrderByPlacaAsc(String adesivo);

	public Boolean existsByPlaca(String placa);

	public Boolean existsByPlacaAndIdNot(String placa, Long id);

	public Boolean existsByAdesivoAndIdNot(String adesivo, Long id);

	public Boolean existsByAdesivo(String adesivo);

	public Boolean existsByPlacaOrAdesivoAndIdNot(String placa, String adesivo, Long id);

	public Boolean existsByPlacaOrAdesivo(String placa, String adesivo);

}
