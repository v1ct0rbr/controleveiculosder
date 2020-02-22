package br.gov.pb.der.controleveiculosder.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.pb.der.controleveiculosder.filtros.UserFiltro;
import br.gov.pb.der.controleveiculosder.model.User;

public interface UserRepositoryCustom {

	public Long countPorFiltro(UserFiltro fs);

	public Page<User> findByFiltro(UserFiltro fs, Pageable page);

	public User findByUsernameOrEmailAndIdNot(String username, String email, Long id);

	public User findByUsernameOrEmail(String username, String email);

}
