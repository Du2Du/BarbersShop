package com.example.barbers_shop.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDTO {
    private String token;
    private String type;
    private Long id;
    private String name;
    private String email;
    
    public static LoginResponseDTO fromUserWithToken(Long id, String name, String email, String token) {
        return LoginResponseDTO.builder()
                .token(token)
                .type("Bearer")
                .id(id)
                .name(name)
                .email(email)
                .build();
    }
}