package com.example.futurodev.interfaces.rest.controller;

import com.example.futurodev.domain.service.UsuarioService;
import com.example.futurodev.interfaces.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MeuPrimeiroController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/nome")
    public String meuNome() {
        return "Eduardo";
    }

    @PostMapping("/usuario")
    public ResponseEntity<String> salvarUsuario(@RequestBody UsuarioDto usuarioDto) {
        usuarioService.salvar(usuarioDto);
        return ResponseEntity.ok("Usuário salvo com sucesso");
    }


}
