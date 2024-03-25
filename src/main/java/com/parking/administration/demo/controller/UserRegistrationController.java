package com.parking.administration.demo.controller;

import com.parking.administration.demo.service.RegistrationService;
import com.parking.administration.dto.request.UserRegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(path = {"/v1/api/registration/", "/v1/user"})
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserRegistrationRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(registrationService.register(request));
    }

    @GetMapping
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token) {
        return ResponseEntity.status(HttpStatus.OK).body("Token successfully confirmed");
    }


}
