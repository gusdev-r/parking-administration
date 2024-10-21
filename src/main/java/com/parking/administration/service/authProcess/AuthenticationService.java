package com.parking.administration.service.authProcess;


import com.parking.administration.domain.AuthDto;
import com.parking.administration.domain.core.User;
import com.parking.administration.dto.TokenDto;
import com.parking.administration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final UserRepository userRepo;
    private final AuthenticationManager authManager;

    public TokenDto authUser(AuthDto authDto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authDto.email(),
                        authDto.password()
                )
        );
        User user = userRepo.findByEmail(authDto.email()).orElseThrow(() -> new NullPointerException("User not found"));
        String jwtToken = jwtService.generateToken(user);

        return TokenDto.builder()
                .token(jwtToken)
                .build();
    }
}
