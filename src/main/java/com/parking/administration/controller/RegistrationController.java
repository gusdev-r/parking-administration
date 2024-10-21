package com.parking.administration.controller;

import com.parking.administration.dto.request.UserRegistrationRequest;
import com.parking.administration.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.parking.administration.util.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = BASE_URL + "/public/registration/")
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserRegistrationRequest request) {
        registrationService.register(request);
    }
    @GetMapping(path = "/confirm/token")
    public void confirmToken(@RequestParam("token") String token) {
        registrationService.confirmToken(token);
    }
}
