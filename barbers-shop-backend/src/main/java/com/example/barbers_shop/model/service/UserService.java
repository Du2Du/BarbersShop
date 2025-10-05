package com.example.barbers_shop.model.service;

import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.barbers_shop.model.dto.UserRequestDTO;
import com.example.barbers_shop.model.dto.UserResponseDTO;
import com.example.barbers_shop.model.entity.User;
import com.example.barbers_shop.model.repository.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.java.Log;

@Service
@Log
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Transactional
  public ResponseEntity<?> save(@Valid UserRequestDTO user) {
    User userEntity = userRepository.findByName(user.getName());
    if (userEntity != null)
      return ResponseEntity.badRequest().body("Já existe um usuário com esse e-mail.");

    try {
      userEntity = userRepository.save(this.mapToUser(user));
      return ResponseEntity.ok(UserResponseDTO.builder().name(user.getName()).email(user.getEmail()).build());
    } catch (Exception e) {
      log.log(Level.SEVERE, "Erro ao salvar usuário", e);
      return ResponseEntity.internalServerError().body("Erro ao salvar usuário.");
    }
  }

  private User mapToUser(UserRequestDTO user) {
    return User.builder()
        .name(user.getName())
        .email(user.getEmail())
        .password(user.getPassword())
        .build();
  }
}
