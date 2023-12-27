package com.nhom1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nhom1.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByEmail(String email);
}
