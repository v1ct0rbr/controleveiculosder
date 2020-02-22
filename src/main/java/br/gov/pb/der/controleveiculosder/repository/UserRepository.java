package br.gov.pb.der.controleveiculosder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.gov.pb.der.controleveiculosder.model.User;
import br.gov.pb.der.controleveiculosder.repository.custom.UserRepositoryCustom;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
	public User findByUsername(String username);

	public User findByUsernameAndPassword(String username, String password);

	public User findByEmail(String email);

	public User findByUsernameOrEmail(String username, String email);

}