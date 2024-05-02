package com.parking.administration.auth;


import com.parking.administration.domain.User;
import com.parking.administration.dto.AuthenticationRequest;
import com.parking.administration.dto.AuthenticationResponse;
import com.parking.administration.infra.exception.EmailException;
import com.parking.administration.infra.exception.enums.ErrorCode;
import com.parking.administration.jwt.JwtService;
import com.parking.administration.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationResponse authenticateUser(AuthenticationRequest request) {

// todo add a try and catch or use the exception handler configured

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EmailException(ErrorCode.EM0004.getMessage(), ErrorCode.EM0004.getCode()));

        var jwtToken = jwtService.generateToken(user);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
