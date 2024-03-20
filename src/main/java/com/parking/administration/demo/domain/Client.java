package com.parking.administration.demo.domain;

import com.parking.administration.demo.customValidator.annotations.ValidateCPF;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "full_name", nullable = false, length = 140)
    private String fullName;
    @Column(name = "email", nullable = false, length = 70)
    private String email;
    @Column(name = "password", nullable = false, length = 15)
    private String password;
    @ValidateCPF
    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;
    @Column(name = "created_at", nullable = false, length = 70)
    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false, length = 70)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicleList;

    @OneToMany(mappedBy = "user")
    private List<ParkingSpace> parkingSpace;


    public Client(Long id, String fullName, String email, String cpf, LocalDateTime createdAt, LocalDateTime updatedAt
            , String password) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.cpf = cpf;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = updatedAt;
        this.password = password;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
