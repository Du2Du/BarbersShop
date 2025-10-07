package com.example.barbers_shop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.barbers_shop.model.dto.request.LoginRequestDTO;
import com.example.barbers_shop.model.dto.request.UserRequestDTO;
import com.example.barbers_shop.model.service.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/save")
  public ResponseEntity<?> save(@RequestBody UserRequestDTO dto) {
    return userService.save(dto);
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
    return userService.login(dto);
  }

}
