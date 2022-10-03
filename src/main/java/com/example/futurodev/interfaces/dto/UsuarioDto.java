package com.example.futurodev.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private String nome;
    private String cpf;
    private String rua;
    private String cidade;
    private String cep;
    private String estado;
}
