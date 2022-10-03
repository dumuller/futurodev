package com.example.futurodev.domain.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UsuarioModel {
    private String nome;
    private String cpf;

    //parte 2
    private String scoreSerasa;
}
