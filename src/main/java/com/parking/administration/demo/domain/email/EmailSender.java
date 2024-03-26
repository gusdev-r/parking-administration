package com.parking.administration.demo.domain.email;

public interface EmailSender {
    void send(String to, String email);
}
