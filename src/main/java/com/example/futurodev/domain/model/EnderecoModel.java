package com.example.futurodev.domain.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EnderecoModel {
    private Long id;
    private Long user_id;
    private String rua;
    private String cidade;
    private String cep;
    private String estado;
}
