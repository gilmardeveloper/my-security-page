package com.example.mysecuritypage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.mysecuritypage.model.Auth;

public interface AuthRepository extends CrudRepository<Auth, Long> {

	Auth findByRoleName(String roleName);

	@Query("select a from Auth a")
	List<Auth> listAll();
	
	@Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Auth a WHERE a.roleName = :proleName")
	boolean existsByRoleName(@Param("proleName") String roleName);

}
