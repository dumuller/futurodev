package com.example.futurodev.security.domain.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.futurodev.interfaces.dto.TokenDto;
import com.example.futurodev.security.exception.InvalidJwtAuthenticationExcpetion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key}")
    private String secretKey;

    @Value("${security.jwt.token.expire-length}")
    private long tempoExpiracaoEmMilisegundos;

    @Value("${security.jwt.token.expire-refresh}")
    private long tempoExpiracaoRefreshEmMilisegundos;

    @Autowired
    private UserDetailsService userDetailsService;

    Algorithm algorithm = null;

    @PostConstruct //após adicionar no conteiner de injeção de dependência e antes de subir a aplicação
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        algorithm = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public TokenDto createAccessToken(String userName, List<String> roles) {
        Date dataAtual = new Date();
        Date dataValidade = new Date(dataAtual.getTime() + tempoExpiracaoEmMilisegundos);
        String tokenAcesso = gerarToken(userName, roles, dataAtual, dataValidade);
        String tokenRefresh = gerarRefreshToken(userName, roles, dataAtual);
        return new TokenDto(userName, true, dataAtual, dataValidade, tokenAcesso, tokenRefresh);
    }

    private String gerarToken(String userName, List<String> roles, Date dataAtual, Date dataValidade) {
        String urlSolicitanteToken = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(dataAtual)
                .withExpiresAt(dataValidade)
                .withSubject(userName)
                .withIssuer(urlSolicitanteToken).sign(algorithm).strip();
    }
    private String gerarRefreshToken(String userName, List<String> roles, Date dataAtual) {
        var dataValidadeRefresh = new Date(dataAtual.getTime() + tempoExpiracaoRefreshEmMilisegundos);
        return JWT.create()
                .withClaim("roles", roles)
                .withIssuedAt(dataAtual)
                .withExpiresAt(dataValidadeRefresh)
                .withSubject(userName)
                .sign(algorithm).strip();
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(decodedJWT.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm algorithmDecoder = Algorithm.HMAC256(secretKey.getBytes(StandardCharsets.UTF_8));
        JWTVerifier jwtVerifier = JWT.require(algorithmDecoder).build();
        return jwtVerifier.verify(token);
    }

    public String resolveToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }

    public boolean validarToken(String token) {
        try {
            DecodedJWT tokenDecodificado = decodedToken(token);
            if (tokenDecodificado.getExpiresAt().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationExcpetion("Token expirado ou inválido");
        }
    }
}
