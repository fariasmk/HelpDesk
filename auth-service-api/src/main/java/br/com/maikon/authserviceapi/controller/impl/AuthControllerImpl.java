package br.com.maikon.authserviceapi.controller.impl;

import br.com.maikon.authserviceapi.controller.AuthController;

import br.com.maikon.authserviceapi.security.JWTAuthenticationImpl;
import br.com.maikon.authserviceapi.services.RefreshTokenService;
import br.com.maikon.authserviceapi.utils.JWTUtils;
import com.maikon.hdcommonslib.models.requests.AuthenticateRequest;
import com.maikon.hdcommonslib.models.requests.RefreshTokenRequest;
import com.maikon.hdcommonslib.models.responses.AuthenticationResponse;
import com.maikon.hdcommonslib.models.responses.RefreshTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

    private final JWTUtils jwtUtils;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RefreshTokenService refreshTokenService;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(final AuthenticateRequest request) throws Exception {
        return ResponseEntity.ok().body(
                new JWTAuthenticationImpl(jwtUtils, authenticationConfiguration.getAuthenticationManager())
                        .authenticate(request)
                        .withRefreshToken(refreshTokenService.save(request.email()).getId())
        );
    }

    @Override
    public ResponseEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest request) {
        return ResponseEntity.ok().body(
                refreshTokenService.refreshToken(request.refreshToken())
        );
    }
}
