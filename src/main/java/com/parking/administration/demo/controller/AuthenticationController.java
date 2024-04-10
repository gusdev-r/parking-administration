package com.parking.administration.demo.controller;

import com.parking.administration.demo.dto.request.AuthenticationRequest;
import com.parking.administration.demo.dto.response.AuthenticationResponse;
import com.parking.administration.demo.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = {"/api/auth/", "/api/auth"})
public class AuthenticationController {
    private AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    public AuthenticationController() {
    }
    @PostMapping()
    public ResponseEntity<AuthenticationResponse> authenticateUser(AuthenticationRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authenticateUser(request));
    }
}
