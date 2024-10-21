package com.stylesystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stylesystem.model.Users;

public interface UsersRepository extends JpaRepository<Users, String>{
    Users findByUserId(String userId);

	Users findByUserName(String userName);
}
