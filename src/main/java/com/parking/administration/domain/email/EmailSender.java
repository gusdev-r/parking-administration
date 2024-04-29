package com.parking.administration.domain.email;

public interface EmailSender {
    void send(String to, String email);
}
