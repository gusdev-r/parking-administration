package com.parking.administration.domain;

import lombok.NonNull;

public record AuthDto(@NonNull String email, @NonNull String password) {
}
