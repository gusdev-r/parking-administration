package com.parking.administration.demo.dto.request;

public record UserRegistrationRequest(
        String fullName,
        String email,
        String password,
        String document,
        String username
        ){
}
