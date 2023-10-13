package br.com.maikon.authserviceapi.services;

import br.com.maikon.authserviceapi.models.RefreshToken;
import br.com.maikon.authserviceapi.repositories.RefreshTokenRepository;
import br.com.maikon.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.maikon.authserviceapi.utils.JWTUtils;
import com.maikon.hdcommonslib.models.exceptions.RefreshTokenExpired;
import com.maikon.hdcommonslib.models.exceptions.ResourceNotFoundException;
import com.maikon.hdcommonslib.models.responses.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration-sec.refresh-token}")
    private Long refreshTokenExpirationSec;

    private final RefreshTokenRepository repository;
    private final UserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;

    public RefreshToken save(final String username) {
        return repository.save(
                RefreshToken.builder()
                        .id(UUID.randomUUID().toString())
                        .createdAt(now())
                        .expiresAt(now().plusSeconds(refreshTokenExpirationSec))
                        .username(username)
                        .build()
        );
    }

    public RefreshTokenResponse refreshToken(final String refreshTokenId){
        final var refreshToken = repository.findById(refreshTokenId)//Verifica se o refresh token existe
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token not found. Id: " + refreshTokenId));

        if(refreshToken.getExpiresAt().isBefore(now())){//Verifica se o refresh token expirou
            throw new RefreshTokenExpired("Refresh token expired. Id: " + refreshTokenId);
        }

        return new RefreshTokenResponse(
                jwtUtils.generateToken((UserDetailsDTO) userDetailsService.loadUserByUsername(refreshToken.getUsername()))
        );
    }
}