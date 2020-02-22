package br.gov.pb.der.controleveiculosder.security;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import br.gov.pb.der.controleveiculosder.filtros.UserFiltro;
import br.gov.pb.der.controleveiculosder.model.User;
import br.gov.pb.der.controleveiculosder.view.UserView;

public interface CustomUserDetailsService extends UserDetailsService {

	public List<GrantedAuthority> getGrantedAuthorities(User user);

	public void salvar(User user);

	public void salvar2(User user);

	public User findByUsername(String username);

	@PreAuthorize("hasAuthority('SUPERUSER')")
	public void excluir(Long id);

	public List<User> findAll();

	public Page<User> filtrar(UserFiltro filtro, Pageable pageable);

	public User findById(Long id);

	public User findUserByUsernameOrEmail(String username, String email);

	public UserView findUserViewById(Long id);

	public User findUserByUsernameOrEmailAndIdNot(String username, String email, Long id);

	public List<UserView> findUserViewByNome(String nome);

	public List<UserView> listarTodosUsuarios();

}
