package br.gov.pb.der.controleveiculosder.repository.custom;

import java.util.List;

import br.gov.pb.der.controleveiculosder.filtros.SetorFiltro;
import br.gov.pb.der.controleveiculosder.model.Setor;
import br.gov.pb.der.controleveiculosder.view.SetorView;

public interface SetorRepositoryCustom extends GenericRepositoryCustom<Integer, Setor, SetorFiltro> {

	public List<SetorView> listarTodos();
}
