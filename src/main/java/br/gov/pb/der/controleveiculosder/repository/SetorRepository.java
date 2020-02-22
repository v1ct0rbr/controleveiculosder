package br.gov.pb.der.controleveiculosder.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pb.der.controleveiculosder.model.Setor;
import br.gov.pb.der.controleveiculosder.repository.custom.SetorRepositoryCustom;

public interface SetorRepository extends JpaRepository<Setor, Integer>, SetorRepositoryCustom {

	public List<Setor> findAllByOrderByNomeAsc();

	public Page<Setor> findByNomeContaining(String nome, Pageable page);

	public Page<Setor> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable page);

	public Page<Setor> findByNomeContainingIgnoreCaseOrderByIdAsc(String nome, Pageable page);

	public Page<Setor> findByNomeContainingIgnoreCaseOrderByNomeDesc(String nome, Pageable page);

	public Page<Setor> findByNomeContainingIgnoreCaseOrderByIdDesc(String nome, Pageable page);

	public List<Setor> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

	public Setor findByNomeAndIdNotIn(String nome, Integer id);

}
