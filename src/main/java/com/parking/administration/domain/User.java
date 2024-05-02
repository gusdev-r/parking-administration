package com.parking.administration.domain;

import com.parking.administration.customValidator.annotations.ValidateCPFAndCNPJ;
import com.parking.administration.domain.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "tb_user")
public class User implements UserDetails {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
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

    //TODO Set the updatedAt when edit some value by the method updateVehicleAttributes or the methods at the userController

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

   public User(String fullName, String email, String password, String document, String username) {
       this.userRole = null;
       this.fullName = fullName;
       this.email = email;
       this.password = password;
       this.document = document;
       this.username = username;
   }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRole.getAuthorities();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(fullName, user.fullName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(document, user.document) && Objects.equals(username, user.username) && userRole == user.userRole && Objects.equals(locked, user.locked) && Objects.equals(enabled, user.enabled) && Objects.equals(createdAt, user.createdAt) && Objects.equals(updatedAt, user.updatedAt) && Objects.equals(vehicleList, user.vehicleList) && Objects.equals(parkingSpaceList, user.parkingSpaceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullName, email, password, document, username, userRole, locked, enabled, createdAt, updatedAt, vehicleList, parkingSpaceList);
    }
}
