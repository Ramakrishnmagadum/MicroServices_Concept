package com.MicroUserService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MicroUserService.Entites.User;

public interface UserRepository extends JpaRepository<User, String> {

}
