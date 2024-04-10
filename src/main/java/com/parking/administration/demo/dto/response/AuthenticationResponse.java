package com.parking.administration.demo.dto.response;

public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }
    public AuthenticationResponse() {
    }
    public static AuthenticationResponseBuilder builder() {
        return new AuthenticationResponseBuilder();
    }

    public static class AuthenticationResponseBuilder {
        private String token;

        private AuthenticationResponseBuilder() {
        }

        public AuthenticationResponseBuilder token(String token) {
            this.token = token;
            return this;
        }

        public AuthenticationResponse build() {
            return new AuthenticationResponse(token);
        }
    }
}
