package com.parking.administration.demo.repository;

import com.parking.administration.demo.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
