package com.parking.administration.dto.request;

import com.parking.administration.domain.enums.UserRole;

public record UserRegistrationRequest(
        String fullName,
        String email,
        String password,
        UserRole userRole,
        String document,
        String username
        ){
}
