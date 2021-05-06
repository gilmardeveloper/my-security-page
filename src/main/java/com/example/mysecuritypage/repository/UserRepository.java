package com.example.mysecuritypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.mysecuritypage.model.User;


public interface UserRepository extends CrudRepository<User, Long>{
	
	User findByEmail(String email);

	@Query("select u from User u")
	List<User> listAll();
		
	@Query("select u from User u where u.id = :pid")
	User findOneById(@Param("pid") Long id);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :pemail")
	boolean existsByEmail(@Param("pemail") String email);

	@Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :pemail and u.id != :pid")
	boolean existsByEmail(@Param("pemail") String email, @Param("pid") Long id);
		

}
