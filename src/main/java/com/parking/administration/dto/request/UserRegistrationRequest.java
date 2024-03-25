package com.parking.administration.dto.request;

public record RegistrationRequest(
        String fullName,
        String email,
        String password
        ){
}
