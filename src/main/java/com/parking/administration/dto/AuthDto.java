package com.parking.administration.dto;

import lombok.NonNull;

public record AuthDto(@NonNull String email, @NonNull String password) {
}
