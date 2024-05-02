package com.parking.administration.controller;

import com.parking.administration.dto.request.UserRegistrationRequest;
import com.parking.administration.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = {"/v1/api/registration/", "/v1/api/registration"})
public class UserRegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.register(request));
    }
    @GetMapping("/confirm/token")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token) {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.confirmToken(token));
    }
}
