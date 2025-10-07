package com.example.barbers_shop.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.barbers_shop.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  
  public User findByName(String name);
  public User findByEmail(String email);
}
