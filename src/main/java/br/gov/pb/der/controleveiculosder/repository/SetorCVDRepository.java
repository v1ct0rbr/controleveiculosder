package br.gov.pb.der.controleveiculosder.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.repository.custom.SetorCVDRepositoryCustom;

public interface SetorCVDRepository extends JpaRepository<SetorCVD, Integer>, SetorCVDRepositoryCustom {

	public List<SetorCVD> findAllByOrderByNomeAsc();

	public Page<SetorCVD> findByNomeContaining(String nome, Pageable page);

	public Page<SetorCVD> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome, Pageable page);

	public Page<SetorCVD> findByNomeContainingIgnoreCaseOrderByIdAsc(String nome, Pageable page);

	public Page<SetorCVD> findByNomeContainingIgnoreCaseOrderByNomeDesc(String nome, Pageable page);

	public Page<SetorCVD> findByNomeContainingIgnoreCaseOrderByIdDesc(String nome, Pageable page);

	public List<SetorCVD> findByNomeContainingIgnoreCaseOrderByNomeAsc(String nome);

	public SetorCVD findByNomeAndIdNotIn(String nome, Integer id);

	public Boolean existsByNomeAndSecretariaId(String nome, Integer secretariaId);

	public Boolean existsByNomeAndSecretariaIdAndIdNot(String nome, Integer secretariaId, Integer id);
}
