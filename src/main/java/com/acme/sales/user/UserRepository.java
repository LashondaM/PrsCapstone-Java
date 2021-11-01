package com.acme.sales.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer>{
	Optional<User> findByUsernameAndPassword(String username, String password);
	

	@Query("SELECT u from users u where u.lastname = ?1")
	List<User> findByLastname(String lastname);
}

//@Query("select u from User where u.username = ?1 and u.password = ?1")
//User findByLogin(String username, String password);
//