package com.example.futurodev.domain.service;

import com.example.futurodev.domain.model.EnderecoModel;
import com.example.futurodev.domain.model.UsuarioModel;
import com.example.futurodev.domain.repository.UsuarioRepository;
import com.example.futurodev.interfaces.dto.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public void salvar(UsuarioDto usuarioDto) {
        UsuarioModel usuarioParaSalvar = converterUsuarioDtoParaUsuario(usuarioDto);
        buscaScoreSerasa(usuarioParaSalvar);
        usuarioRepository.salvarUsuario(usuarioParaSalvar);
        System.out.println("Usuário: " + usuarioParaSalvar.toString());

        EnderecoModel enderecoParaSalvar = converterUsuarioDtoParaEndereco(usuarioDto);
        usuarioRepository.salvarEndereco(enderecoParaSalvar);
        System.out.println("Endereco: " + enderecoParaSalvar.toString());

        System.out.println("Usuário e endereço salvo com sucesso!");
    }

    private void buscaScoreSerasa(UsuarioModel usuarioModel) {
        String scoreSerasa = "BOM"; //metodo que faz chamada para o serasa e pega o retorno
        usuarioModel.setScoreSerasa(scoreSerasa);
    }

    private EnderecoModel converterUsuarioDtoParaEndereco(UsuarioDto usuarioDto) {
        EnderecoModel enderecoModel = new EnderecoModel();
        enderecoModel.setId(1L);
        enderecoModel.setUser_id(1L);
        enderecoModel.setCep(usuarioDto.getCep());
        enderecoModel.setCidade(usuarioDto.getCidade());
        enderecoModel.setEstado(usuarioDto.getEstado());
        enderecoModel.setRua(usuarioDto.getRua());
        return enderecoModel;
    }

    private UsuarioModel converterUsuarioDtoParaUsuario(UsuarioDto usuarioDto) {
        UsuarioModel usuarioModel = new UsuarioModel();
        usuarioModel.setCpf(usuarioDto.getCpf());
        usuarioModel.setNome(usuarioDto.getNome());
        return usuarioModel;
    }
}
