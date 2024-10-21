package com.parking.administration.service;


import com.parking.administration.domain.core.User;
import com.parking.administration.dto.AuthenticationRequest;
import com.parking.administration.dto.AuthenticationResponse;
import com.parking.administration.infra.exception.EmailException;
import com.parking.administration.infra.exception.enums.ErrorCode;
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

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EmailException(ErrorCode.EM0004.getMessage(), ErrorCode.EM0004.getCode()));

        authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                )
        );
        var jwtToken = jwtService.generateToekn(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
