package com.parking.administration.auth;

import com.parking.administration.dto.AuthenticationRequest;
import com.parking.administration.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"v1/api/auth/", "/api/auth"})
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<AuthenticationResponse> authenticateUser(AuthenticationRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticateUser(request));
    }
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World!");
    }
}
