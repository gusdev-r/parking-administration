package com.parking.administration.controller;

import com.parking.administration.dto.AuthDto;
import com.parking.administration.dto.TokenDto;
import com.parking.administration.service.authProcess.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.parking.administration.util.Constants.BASE_URL;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = BASE_URL + "/public/auth/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<TokenDto> authenticateUser(@RequestBody AuthDto request) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.authUser(request));
    }

    @PostMapping("refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World!");
    }
}
