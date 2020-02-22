package br.gov.pb.der.controleveiculosder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pb.der.controleveiculosder.model.Secretaria;
import br.gov.pb.der.controleveiculosder.repository.custom.SecretariaRepositoryCustom;

public interface SecretariaRepository extends JpaRepository<Secretaria, Integer>, SecretariaRepositoryCustom {

	public Secretaria findByNomeAndIdNotIn(String nome, Integer id);

	public List<Secretaria> findByOrderByNomeAsc();

	public Boolean existsByNomeAndIdNot(String nome, Integer id);

	public Boolean existsByNome(String nome);

}
