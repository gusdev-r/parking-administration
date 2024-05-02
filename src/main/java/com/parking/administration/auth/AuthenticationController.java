package com.parking.administration.controller;

import com.parking.administration.dto.AuthenticationRequest;
import com.parking.administration.dto.AuthenticationResponse;
import com.parking.administration.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = {"/api/auth/", "/api/auth"})
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping()
    public ResponseEntity<AuthenticationResponse> authenticateUser(AuthenticationRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticateUser(request));
    }
}
