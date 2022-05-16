package com.hdfc.api.user.v1;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
	UserModel findByUserEmail(String email);


	@Query("SELECT s FROM UserModel s")
	Page<UserModel> findAllUsers(Pageable pageable);


	
}
