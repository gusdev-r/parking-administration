package com.parking.administration.demo.domain;

import com.parking.administration.demo.customValidator.annotations.ValidateCPFAndCNPJ;
import com.parking.administration.demo.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 140)
    private String fullName;

    @Column(name = "email", nullable = false, length = 70)
    private String email;

    @Column(name = "password", nullable = false, length = 15)
    private String password;

    @ValidateCPFAndCNPJ
    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", nullable = false)
    private UserRole userRole;

    private Boolean locked;

    private Boolean enabled;

    @Column(name = "created_at", nullable = false, length = 70)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, length = 70)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicleList;

    @OneToMany(mappedBy = "user")
    private List<ParkingSpace> parkingSpaceList;


    public User(String fullName, String email, String password, String document, String username, UserRole userRole,
                Boolean locked, Boolean enabled, LocalDateTime createdAt, LocalDateTime updatedAt,
                List<Vehicle> vehicleList, List<ParkingSpace> parkingSpaceList) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.document = document;
        this.username = username;
        this.userRole = userRole;
        this.locked = false;
        this.enabled = false;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.vehicleList = vehicleList;
        this.parkingSpaceList = parkingSpaceList;
    }

   public User(UserRole userRole, String fullName, String email, String password, String document, String username) {
       this.userRole = userRole;
       this.fullName = fullName;
       this.email = email;
       this.password = password;
       this.document = document;
       this.username = username;
   }

    public User() {
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public List<ParkingSpace> getParkingSpaceList() {
        return parkingSpaceList;
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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(userRole.name());
        return Collections.singletonList(simpleGrantedAuthority);
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { //search the configuration at the system of this implementation
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
