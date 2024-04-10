package com.parking.administration.demo.dto.request;

import com.parking.administration.demo.domain.enums.UserRole;

public record UserRegistrationRequest(
        String fullName,
        String email,
        String password,
        UserRole userRole,
        String document,
        String username
        ){

        public static UserRegistrationRequestBuilder builder() {
                return new UserRegistrationRequestBuilder();
        }
        public static class UserRegistrationRequestBuilder {
                private String fullName;
                private String email;
                private String password;
                private UserRole userRole;
                private String document;
                private String username;

                private UserRegistrationRequestBuilder() {
                }

                public UserRegistrationRequestBuilder fullName(String fullName) {
                        this.fullName = fullName;
                        return this;
                }

                public UserRegistrationRequestBuilder email(String email) {
                        this.email = email;
                        return this;
                }

                public UserRegistrationRequestBuilder password(String password) {
                        this.password = password;
                        return this;
                }

                public UserRegistrationRequestBuilder userRole(UserRole userRole) {
                        this.userRole = userRole;
                        return this;
                }

                public UserRegistrationRequestBuilder document(String document) {
                        this.document = document;
                        return this;
                }

                public UserRegistrationRequestBuilder username(String username) {
                        this.username = username;
                        return this;
                }

                public UserRegistrationRequest build() {
                        return new UserRegistrationRequest(fullName, email, password, userRole, document, username);
                }
        }
}
