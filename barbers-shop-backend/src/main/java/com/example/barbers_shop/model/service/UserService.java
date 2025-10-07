package com.example.barbers_shop.model.service;

import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.barbers_shop.model.dto.request.LoginRequestDTO;
import com.example.barbers_shop.model.dto.request.UserRequestDTO;
import com.example.barbers_shop.model.dto.response.LoginResponseDTO;
import com.example.barbers_shop.model.dto.response.UserResponseDTO;
import com.example.barbers_shop.model.entity.User;
import com.example.barbers_shop.model.repository.UserRepository;
import com.example.barbers_shop.util.JwtUtil;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.java.Log;

@Service
@Log
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  UserDetailsService userDetailsService;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtUtil jwtUtil;

  private User mapToUser(UserRequestDTO user) {
    return User.builder()
        .name(user.getName())
        .email(user.getEmail())
        .password(passwordEncoder.encode(user.getPassword())) // Criptografar senha
        .build();
  }

  @Transactional
  public ResponseEntity<?> save(@Valid UserRequestDTO user) {
    User userEntity = userRepository.findByEmail(user.getEmail()); // Buscar por email
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

  public ResponseEntity<?> login(@Valid LoginRequestDTO dto) {
    try {
      User userEntity = userRepository.findByEmail(dto.getEmail());
      
      if (userEntity == null) {
        return ResponseEntity.badRequest().body("Usuário não encontrado.");
      }
      
      if (!passwordEncoder.matches(dto.getPassword(), userEntity.getPassword())) {
        return ResponseEntity.badRequest().body("Senha incorreta.");
      }
      
      String token = jwtUtil.generateToken(userEntity);
      
      LoginResponseDTO response = LoginResponseDTO.fromUserWithToken(
          userEntity.getId(),
          userEntity.getName(),
          userEntity.getEmail(),
          token
      );
      
      return ResponseEntity.ok(response);
      
    } catch (Exception e) {
      log.log(Level.SEVERE, "Erro ao fazer login", e);
      return ResponseEntity.internalServerError().body("Erro interno do servidor.");
    }
  }
}
