package br.gov.pb.der.controleveiculosder.security;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.gov.pb.der.controleveiculosder.filtros.UserFiltro;
import br.gov.pb.der.controleveiculosder.model.Role;
import br.gov.pb.der.controleveiculosder.model.Setor;
import br.gov.pb.der.controleveiculosder.model.User;
import br.gov.pb.der.controleveiculosder.repository.UserRepository;
import br.gov.pb.der.controleveiculosder.service.MyAbstractService;
import br.gov.pb.der.controleveiculosder.service.SetorService;
import br.gov.pb.der.controleveiculosder.utils.Funcoes;
import br.gov.pb.der.controleveiculosder.view.UserView;

//import br.gov.pb.der.deros.model.Role;
//import br.gov.pb.der.deros.model.Setor;
//import br.gov.pb.der.deros.model.User;
//import br.gov.pb.der.deros.model.filtros.UserFiltro;
//import br.gov.pb.der.deros.model.view.ClienteView;
//import br.gov.pb.der.deros.model.view.UserView;
//import br.gov.pb.der.deros.repository.UserRepository;
//import br.gov.pb.der.deros.services.MyAbstractService;
//import br.gov.pb.der.deros.services.SetorService;
//import br.gov.pb.der.deros.utils.Funcoes;

@Component
public class CustomUserDetailsServiceImpl extends MyAbstractService implements CustomUserDetailsService {
	/**
	 * 
	 */
	@PersistenceContext
	EntityManager em;

	@Autowired
	private final UserRepository userRepository;

	@Autowired
	private SetorService setorService;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	public CustomUserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		System.out.println("User : " + user);
		if (null == user) {
			throw new UsernameNotFoundException("No user present with username: " + username);
		} else {
			return new CustomUserDetails(user, null);
		}
	}

	@Override
	public User findUserByUsernameOrEmail(String username, String email) throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmail(username, email);
		return user;
	}

	@Override
	public User findUserByUsernameOrEmailAndIdNot(String username, String email, Long id)
			throws UsernameNotFoundException {
		User user = userRepository.findByUsernameOrEmailAndIdNot(username, email, id);
		return user;
	}

	@Override
	public List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		for (Role userProfile : user.getRoles()) {
			System.out.println("UserProfile : " + userProfile);
			authorities.add(new SimpleGrantedAuthority("ROLE_" + userProfile.getAuthority()));
		}
		System.out.print("authorities :" + authorities);
		return authorities;
	}

	@Transactional
	@Override
	public void salvar(User user) {
		user.setTokenSessao(Funcoes.cryptPasswordSha256(Calendar.getInstance().toString()));

		if (user.getSetor() != null && Funcoes.isValidIntegerParameter(user.getSetor().getId())) {
			Setor setor = setorService.findById(user.getSetor().getId());
			if (setor != null)
				user.setSetor(setor);
			else
				user.setSetor(null);
		} else {
			user.setSetor(null);
		}

		if (user.getId() != null && user.getId() > 0) {
			User userTemp = findById(user.getId());

			if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
				user.setPassword(userTemp.getPassword());
			} else {
				user.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			user.setDataCadastro(userTemp.getDataCadastro());
		} else {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setDataCadastro(Calendar.getInstance());
		}

		userRepository.save(user);

	}

	public void salvar2(User user) {

		userRepository.save(user);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void excluir(Long id) {
		userRepository.deleteById(id);

	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	public Page<User> filtrar(UserFiltro filtro, Pageable pageable) {
		// TODO Auto-generated method stub
		return userRepository.findByFiltro(filtro, pageable);

	}

	public User findById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).get();
	}

	@Override
	public List<UserView> listarTodosUsuarios() {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.deros.model.view.UserView(u.id, u.name, u.email, u.username) FROM User AS u";
		TypedQuery<UserView> query = em.createQuery(queryStr, UserView.class);
		List<UserView> results = query.getResultList();
		return results;
	}

	@Override
	public List<UserView> findUserViewByNome(String nome) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.deros.model.view.UserView(u.id, u.name, u.email, u.username) FROM User AS u where lower(u.nome) like :nome";
		TypedQuery<UserView> query = em.createQuery(queryStr, UserView.class);
		query.setParameter("nome", Funcoes.toLowerCaseForLike(nome));

		List<UserView> results = query.getResultList();
		return results;
	}

	@Override
	public UserView findUserViewById(Long id) {
		// TODO Auto-generated method stub
		String queryStr = "SELECT NEW br.gov.pb.der.deros.model.view.UserView(u.id, u.name, u.email, u.username) FROM User AS u where u.id = :id";
		TypedQuery<UserView> query = em.createQuery(queryStr, UserView.class);
		query.setParameter("id", id);
		UserView results = query.getSingleResult();
		return results;
	}

}
