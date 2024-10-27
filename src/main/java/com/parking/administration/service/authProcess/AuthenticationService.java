package com.parking.administration.service.authProcess;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.administration.domain.token.Token;
import com.parking.administration.dto.AuthDto;
import com.parking.administration.domain.core.User;
import com.parking.administration.dto.TokenDto;
import com.parking.administration.repository.TokenRepository;
import com.parking.administration.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepo;
    private final TokenRepository tknRepo;
    private final AuthenticationManager authManager;

    public TokenDto authUser(AuthDto authDto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.email(),
                        authDto.password()
                )
        );
        User user = userRepo.findByEmail(authDto.email()).orElseThrow(() -> new NullPointerException("User not found"));
        String accessTkn = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessTkn);
        String refreshTkn = jwtService.generateRefreshToken(user);
        return TokenDto.builder()
                .accessToken(accessTkn)
                .refreshToken(refreshTkn)
                .build();
    }

    private void revokeAllUserTokens(User user) {
        List<Token> tokenList = tknRepo.findAllValidTokeByUser(user.getId());
        if(tokenList.isEmpty()) {
            return;
        }
        tokenList.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tknRepo.saveAll(tokenList);
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .expired(false)
                .revoked(false)
                .build();
        tknRepo.save(token);
    }


    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);

        String subExt = jwtService.extractSub(refreshToken);

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userRepo.findByEmail(subExt).orElseThrow();
            if (jwtService.validateToken(refreshToken, userDetails)) {
                TokenDto responseDto =  TokenDto.builder()
                        .accessToken(jwtService.generateToken(userDetails))
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), responseDto);
            }
        }
    }
}
