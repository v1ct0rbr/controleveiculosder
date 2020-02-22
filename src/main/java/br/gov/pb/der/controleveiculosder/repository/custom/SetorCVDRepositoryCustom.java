package br.gov.pb.der.controleveiculosder.repository.custom;

import java.util.List;

import br.gov.pb.der.controleveiculosder.filtros.SetorFiltro;
import br.gov.pb.der.controleveiculosder.model.SetorCVD;
import br.gov.pb.der.controleveiculosder.view.SetorView;

public interface SetorCVDRepositoryCustom extends GenericRepositoryCustom<Long, SetorCVD, SetorFiltro> {

	public List<SetorView> listarTodos();

	public List<SetorView> findBySecretariaIdOrderByNomeAsc(Integer id);
}
