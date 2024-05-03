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
    public String register(@Valid @RequestBody UserRegistrationRequest request) {
        return registrationService.register(request);
    }
    @GetMapping(path = "/confirm/token")
    public String confirmToken(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}
