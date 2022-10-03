package com.example.futurodev.security.domain.service;

import com.example.futurodev.security.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Logger logger = Logger.getLogger(UserService.class.getName());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Buscando um usuário pelo nome");
        var user = userRepository.findByUserName(username);
        if (user != null) {
            user.getPermissions().forEach(permission -> logger.info(permission.getDescription()));
            return user;
        } else {
            throw new UsernameNotFoundException("Usuário não encontrado. Usuário: ".concat(username));
        }
    }
}
